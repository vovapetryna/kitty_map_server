package com.example.kitty.repositories;

import com.example.kitty.dto.PointFilterDto;
import com.example.kitty.entities.enums.Category;
import com.example.kitty.entities.mongo.Attribute;
import com.example.kitty.entities.mongo.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PointCustomRepositoryImpl implements PointCustomRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Point> findAllPointsByTrainingFilterData(PointFilterDto pointFilterData) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();

        List<Category> categories = pointFilterData.getCategories();
        List<Attribute> attributes = pointFilterData.getAttributes();

        if (categories != null) {
            criteria.add(Criteria.where("category").in(categories));
        }
        if (attributes != null && !attributes.isEmpty()) {
            criteria.add(Criteria.where("attributes").all(attributes));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return mongoTemplate.find(query, Point.class);
    }

}


