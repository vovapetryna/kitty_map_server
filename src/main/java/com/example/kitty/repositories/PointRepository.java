package com.example.kitty.repositories;

import com.example.kitty.entities.mongo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointRepository extends MongoRepository<Point, String> {

}
