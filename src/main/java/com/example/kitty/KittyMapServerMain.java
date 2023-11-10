package com.example.kitty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class KittyMapServerMain {

	public static void main(String[] args) {
		SpringApplication.run(KittyMapServerMain.class, args);
	}

}
