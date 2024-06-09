package com.example.kitty;

import com.example.kitty.entities.LatLong;
import com.example.kitty.entities.PgEdge;
import com.example.kitty.repositories.PgRoutingRepository;
import com.example.kitty.repositories.PgWeightingRepository;
import com.example.kitty.utils.PBFAnalyzer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;


@SpringBootApplication
@EnableMongoRepositories
@RequiredArgsConstructor
@AutoConfiguration
public class KittyMapServerMain {
    private final PgWeightingRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(KittyMapServerMain.class, args);
    }

    @Bean
    public CommandLineRunner test(ApplicationContext ctx) {
        return args -> {
//            repository.reweigh(Optional.empty());
        };
    }
}
