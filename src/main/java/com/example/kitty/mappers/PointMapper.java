package com.example.kitty.mappers;

import com.example.kitty.dto.PointDto;
import com.example.kitty.entities.mongo.Point;
import org.springframework.stereotype.Service;

@Service
public class PointMapper {
    public Point toModel(PointDto pointDto) {
        return Point.builder()
                .name(pointDto.getName())
                .category(pointDto.getCategory())
                .attributes(pointDto.getAttributes())
                .subCategory(pointDto.getSubCategory())
                .description(pointDto.getDescription())
                .location(pointDto.getLocation())
                .build();
    }

}

