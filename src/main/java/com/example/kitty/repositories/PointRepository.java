package com.example.kitty.repositories;

import com.example.kitty.entities.mongo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PointRepository extends MongoRepository<Point, Long>, PointCustomRepository {
    @Query(value = "{'category': { $ne: 'UNKNOWN' } }")
    List<Point> findAllTaggedWithCategory();

    List<Point> findAllByWasEditedRampIsTrue();

    List<Point> findAllByWasEditedObstacleIsTrue();

    @Query(value = "{'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: 1.5 } } }")
    List<Point> findNearest(double longitude, double latitude);
}
