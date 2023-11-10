package com.example.kitty.dto;

import com.example.kitty.entities.enums.Category;
import com.example.kitty.entities.mongo.Attribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointDto {
    private String name;

    private Category category;

    private GeoJsonPoint location;

    private List<Attribute> attributes;
}
