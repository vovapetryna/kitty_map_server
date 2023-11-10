package com.example.kitty;

import com.example.kitty.services.GraphHopperService;
import com.example.kitty.services.MappingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.stream.StreamSupport;


@SpringBootApplication
@EnableMongoRepositories
@RequiredArgsConstructor
@AutoConfiguration
public class KittyMapServerMain {

	private final GraphHopperService graphHopperService;

	private final MappingService mappingService;

	public static void main(String[] args) {
		SpringApplication.run(KittyMapServerMain.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			mappingService.updatePointsFeatures();
//			System.out.println("DONE");
//		};
//	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			GeoJsonPoint from = new GeoJsonPoint(30.620930191509597, 50.39402865778816);
//			GeoJsonPoint to = new GeoJsonPoint(30.518825271259313, 50.42281479982218);
//			Pair<List<GeoJsonPoint>, Double> routingResults = graphHopperService.getRouteBetweenPoints(from, to);
//			List<GeoJsonPoint> route = routingResults.getLeft();
//			for (GeoJsonPoint point : route) {
//				System.out.println(point.getY() + "," + point.getX() + ",");
//			}
////            collectionToFile();
////            System.out.println("saved");
//		};
//	}
}
