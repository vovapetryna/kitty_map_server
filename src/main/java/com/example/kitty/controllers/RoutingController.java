package com.example.kitty.controllers;

import com.example.kitty.entities.LatLongPair;
import com.example.kitty.services.GraphHopperService;
import com.example.kitty.services.OsmFileEditorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(ControllerAPI.ROUT_CONTROLLER)
@RequiredArgsConstructor
public class RoutingController {
    private final GraphHopperService routingService;
    private final OsmFileEditorService osmFileEditorService;

    @PostMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<List<Double>>> findRoute(@RequestBody LatLongPair latLongPair) {
        return new ResponseEntity<>(routingService.getRouteBetweenPoints(latLongPair).getKey(), HttpStatus.OK);
    }

    @GetMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST + "reload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateGraphCache() throws XPathExpressionException, ParserConfigurationException, IOException, TransformerException, SAXException {
        Boolean wasUpdated = osmFileEditorService.updateXmlFile();
        return new ResponseEntity<>(wasUpdated, HttpStatus.OK);
    }


}
