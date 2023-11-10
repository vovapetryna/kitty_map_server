package com.example.kitty.services;

import com.example.kitty.entities.LatLong;
import com.example.kitty.entities.LatLongPair;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.GraphHopperConfig;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.Profile;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint3D;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GraphHopperService {
    private final S3Service s3Service;

    private GraphHopper hopper;

    private final String routingFolderLocation = "routing";
    private final String osmFileName = "roads_Kyiv.osm";
    private final String graphCacheName = "kyivRoadsCache";

    static List<LatLong> convertPointListToWaypointList(PointList pointList) {
        List<LatLong> wayPoints = new ArrayList<>();
        for (GHPoint3D point : pointList) {
            wayPoints.add(new LatLong(point.lat, point.lon));
        }
        return wayPoints;
    }

    public static ResponsePath routing(GraphHopper hopper, GHRequest req) {
        // simple configuration of the request object
        GHResponse rsp = hopper.route(req);

        // handle errors
        if (rsp.hasErrors())
            throw new RuntimeException(rsp.getErrors().toString());

        // use the best path, see the GHResponse class for more possibilities.
        return rsp.getBest();
    }


    public Pair<List<LatLong>, Double> getRouteBetweenPoints(LatLongPair request) {
        GraphHopper hopper = getGraphHopperInstance();
        GHRequest req = new GHRequest(request.getFrom().getLat(), request.getFrom().getLng(),
            request.getTo().getLat(), request.getTo().getLng())
            .setProfile("wheelchair");
        ResponsePath responsePath = routing(hopper, req);
        PointList pointList = responsePath.getPoints();
        Double pathDistance = responsePath.getDistance();
        return Pair.of(convertPointListToWaypointList(pointList), pathDistance); //getSimplifyPointsList(convertPointListToWaypointList(pointList));
    }

//    public List<AiWayPointDto> getSimplifyPointsList(List<AiWayPointDto> originalList) {
//        if (originalList.size() < 2) {
//            return originalList;
//        }
//        AiWayPointDto[] pointArray = new AiWayPointDto[originalList.size()];
//        int i = 0;
//        for (AiWayPointDto wayPoint : originalList) {
//            pointArray[i++] = wayPoint;
//        }
//        Simplify<AiWayPointDto> aut = new Simplify<>(new AiWayPointDto[0]);
//        AiWayPointDto[] pointsActual = aut.simplify(pointArray, 0.005, true);
//        log.debug("Start length=  {}", pointArray.length);
//        log.debug("Result length=  {}", pointsActual.length);
//        return new ArrayList<>(Arrays.asList(pointsActual));
//    }


    private synchronized GraphHopper getGraphHopperInstance() {

        if (hopper == null) {
            File osmFile = s3Service.downloadFileToLocalStorage("roads_kyiv.osm");

            String cacheLocation = routingFolderLocation + "/" + graphCacheName;
            String osmFileLocation = osmFile.getAbsolutePath();

            hopper = new GraphHopper().init(
                new GraphHopperConfig().
                    putObject("datareader.file", osmFileLocation).
                    putObject("datareader.dataaccess", "RAM").
                    putObject("graph.location", cacheLocation).
                    putObject("graph.vehicles", "wheelchair").
                    putObject("import.osm.ignored_highways", ""). //ootway,cycleway,path,pedestrian,steps,residential,track
                    putObject("index.max_region_search", 100).
                    putObject("ch.disable", true).
                    setProfiles(Collections.singletonList(
                        new Profile("wheelchair").setVehicle("wheelchair").setWeighting("fastest").setTurnCosts(false)
                    ))).setOSMFile(osmFileLocation);
            hopper.importOrLoad();
        }
        return hopper;
    }

    private static void printPoints(PointList pointList) {
        System.out.println("Printing points");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < pointList.size(); ++i) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(pointList.getLat(i));
            sb.append(',');
            sb.append(pointList.getLon(i));
            if (pointList.is3D()) {
                sb.append(',');
                sb.append(pointList.getEle(i));
            }
        }
        System.out.println(sb);
    }

}
