package com.example.kitty.mappers;

import com.example.kitty.dto.PointDto;
import com.example.kitty.entities.mongo.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {
    Point toModel(PointDto groupDto);

    PointDto toDto(Point group);
}

