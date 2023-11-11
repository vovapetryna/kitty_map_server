package com.example.kitty.repositories;

import com.example.kitty.entities.mongo.RawPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RawPointRepository extends MongoRepository<RawPoint, String>{
}
