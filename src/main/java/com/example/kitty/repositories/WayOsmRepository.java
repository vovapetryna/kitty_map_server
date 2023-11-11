package com.example.kitty.repositories;

import com.example.kitty.entities.WayOsm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WayOsmRepository extends MongoRepository<WayOsm, Long> {

    @Query(value = "{'wayline': { $nearSphere: { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: 3 } } }")
    WayOsm findFirstByWaylineNear(double longitude, double latitude);

}
