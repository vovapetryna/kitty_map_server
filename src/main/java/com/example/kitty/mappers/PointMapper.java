package com.example.kitty.mappers;

import com.example.kitty.dto.PointDto;
import com.example.kitty.entities.mongo.Point;
import org.mapstruct.Mapper;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

@Service
public class PointMapper {
    public Point toModel(PointDto pointDto) {
        var location = new GeoJsonPoint(pointDto.getLocation().getLng(), pointDto.getLocation().getLat());

        return Point.builder()
                .name(pointDto.getName())
                .category(pointDto.getCategory())
                .attributes(pointDto.getAttributes())
                .subCategory(pointDto.getSubCategory())
                .description(pointDto.getDescription())
                .location(location)
                .build();
    }

}

