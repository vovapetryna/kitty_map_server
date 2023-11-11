package com.example.kitty.services;

import com.example.kitty.entities.enums.AttributeType;
import com.example.kitty.entities.mongo.Attribute;
import com.example.kitty.entities.mongo.Point;
import com.example.kitty.repositories.PointRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.kitty.services.GraphHopperService.graphCacheName;
import static com.example.kitty.services.GraphHopperService.osmFileName;
import static com.example.kitty.services.GraphHopperService.routingFolderLocation;

@RequiredArgsConstructor
@Service
public class OsmFileEditorService {

    private final PointRepository pointRepository;
    private final S3Service s3Service;

    public Boolean updateXmlFile() throws ParserConfigurationException, IOException, SAXException, TransformerException, XPathExpressionException {
        List<Point> editedRamps = pointRepository.findAllByWasEditedRampIsTrue();
        List<Point> editedObstacle = pointRepository.findAllByWasEditedObstacleIsTrue();


        if (editedRamps.isEmpty() && editedObstacle.isEmpty()) {
            System.out.println("Nothing to save");
            return false;
        }

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(new File(osmFileName));

        System.out.println("Started updating xml file");

        for (Point editedPoint : editedRamps) {
            if (editedPoint.getWayId() == null) {
                continue;
            }
            String rampValue = editedPoint.getAttributes() != null && editedPoint.getAttributes().stream()
                    .map(Attribute::getAttributeType).anyMatch(attributeType -> attributeType.equals(AttributeType.ramp)) ? "yes" : "no";

//            String rampValue = editedPoint.getRamp() != null ? (editedPoint.getRamp() ? "yes" : "no") : null;
//            if (rampValue == null) {
//                continue;
//            }
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/osm/way[@id=\"%d\"]/tag[@k=\"wheelchair\"]".formatted(editedPoint.getWayId());
            Node wheelchairInfo = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
            if (wheelchairInfo == null) {
                xPath = XPathFactory.newInstance().newXPath();
                expression = "/osm/way[@id=\"%d\"]".formatted(editedPoint.getWayId());
                wheelchairInfo = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
                Element tag = doc.createElement("tag");
                tag.setAttribute("k", "wheelchair");
                tag.setAttribute("v", rampValue);
                wheelchairInfo.appendChild(tag);
                //get().getNamedItem("v").setNodeValue("no");
            } else {
                wheelchairInfo.getAttributes().getNamedItem("v").setNodeValue(rampValue);
            }
        }

        for (Point obstacle : editedObstacle) {
            if (obstacle.getWayId() == null) {
                continue;
            }
            String obstacleValue = obstacle.getAttributes() != null && obstacle.getAttributes().stream()
                .map(Attribute::getAttributeType).anyMatch(attributeType -> attributeType.equals(AttributeType.obstacleMarking)) ? "yes" : "no";

//            String rampValue = editedPoint.getRamp() != null ? (editedPoint.getRamp() ? "yes" : "no") : null;
//            if (rampValue == null) {
//                continue;
//            }
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/osm/way[@id=\"%d\"]/tag[@k=\"foot\"]".formatted(obstacle.getWayId());
            Node wheelchairInfo = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
            if (wheelchairInfo == null) {
                xPath = XPathFactory.newInstance().newXPath();
                expression = "/osm/way[@id=\"%d\"]".formatted(obstacle.getWayId());
                wheelchairInfo = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
                Element tag = doc.createElement("tag");
                tag.setAttribute("k", "foot");
                tag.setAttribute("v", obstacleValue);
                wheelchairInfo.appendChild(tag);
                //get().getNamedItem("v").setNodeValue("no");
            } else {
                wheelchairInfo.getAttributes().getNamedItem("v").setNodeValue(obstacleValue);
            }
        }

        System.out.println("Started writing changes to file");

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "no");
        tf.setOutputProperty(OutputKeys.METHOD, "xml");
//        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource domSource = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File(osmFileName));
        tf.transform(domSource, sr);

        Set<Long> savedPoints = new HashSet<>();
        for (Point editedPoint : editedRamps) {
            editedPoint.setWasEditedRamp(false);
            pointRepository.save(editedPoint);
            savedPoints.add(editedPoint.getId());
        }

        for (Point editedPoint : editedObstacle) {
            if (savedPoints.contains(editedPoint.getId())){
                editedPoint = pointRepository.findById(editedPoint.getId()).get();
            }
            editedPoint.setWasEditedObstacle(false);
            pointRepository.save(editedPoint);
        }

        System.out.println("Dropping previous cache");
        FileUtils.deleteDirectory(new File(routingFolderLocation + "/" + graphCacheName));

        System.out.println("Uploading new osm version");
        s3Service.uploadLocalFileToStorage(new File(osmFileName));

        System.out.println("saved");
        return true;
    }
}
