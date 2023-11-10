package com.example.kitty.repositories;

import com.example.kitty.entities.mongo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PointRepository extends MongoRepository<Point, String> {
    @Query(value = "{'category': { $ne: 'UNKNOWN' } }")
    List<Point> findAllTaggedWithCategory();
}
