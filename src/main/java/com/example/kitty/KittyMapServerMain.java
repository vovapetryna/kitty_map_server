package com.example.kitty;

import com.example.kitty.entities.LatLong;
import com.example.kitty.entities.LatLongPair;
import com.example.kitty.repositories.PointRepository;
import com.example.kitty.services.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;


@SpringBootApplication
@EnableMongoRepositories
@RequiredArgsConstructor
@AutoConfiguration
public class KittyMapServerMain {

    private final GraphHopperService graphHopperService;
    private final PointRepository pointRepository;
    private final OsmFileEditorService osmFileEditorService;
    private final MappingService mappingService;
    private final RawPointMappingService rawPointMappingService;
	private final S3Service s3Service;

    public static void main(String[] args) {
        SpringApplication.run(KittyMapServerMain.class, args);
    }

//	@Bean
//	public CommandLineRunner uploadFile(ApplicationContext ctx) {
//		return args -> {
//			s3Service.uploadLocalFileToStorage("roads_kyiv.osm");
//		};
//	}

//    @Bean
//    public CommandLineRunner getLUNData(ApplicationContext ctx) {
//        return args -> {
//            System.out.println(lunParsingService.getGeoFromTiles(12, 2395, 1380));
//            lunParsingService.getTilesData();
//        };
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            rawPointMappingService.mergeIntoCorePointIndex();
//            System.out.println("DONE");
//        };
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            rawPointMappingService.groupPointFeatures();
//            System.out.println("DONE");
//        };
//    }

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
////			mappingService.updatePointsFeatures();
//            mappingService.updateNameBasedOnCategory();
//			System.out.println("DONE");
//		};
//	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
////			System.out.println("Started mapping good values");
//
////			List<Point> allPoints = pointRepository.findAll();
////			for (Point point : allPoints){
////				if (point.getRamp() != null && point.getRamp() && (point.getWheelchair() == null || point.getWheelchair().equals("no"))) {
////					point.setWasEditedRamp(true);
////					pointRepository.save(point);
////				}
////			}
//
////			System.out.println("Started refilling xml file");
////			osmFileEditorService.updateXmlFile();
//
//			LatLong from = new LatLong(50.474493469372675, 30.44883216958482); //50.47429,30.44932
//			LatLong to = new LatLong(50.47453, 30.44796);
//			Pair<List<List<Double>>, Double> routingResults = graphHopperService.getRouteBetweenPoints(new LatLongPair(from, to));
//			List<List<Double>> route = routingResults.getLeft();
//			for (List<Double> coord : route) {
//				System.out.println(coord.get(0) + "," + coord.get(1) + ",");
//			}
//
//            System.out.println("saved");
//		};
//	}
}
//50.4734873,30.4482682,
//50.4735109,30.4480457,
//
//<tag k="traffic_signals:sound" v="yes"/>
//<tag k="traffic_signals:vibration" v="no"/>
//<tag k="wheelchair" v="no"/>