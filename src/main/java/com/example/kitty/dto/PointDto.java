package com.example.kitty.dto;

import com.example.kitty.entities.LatLong;
import com.example.kitty.entities.enums.Category;
import com.example.kitty.entities.mongo.Attribute;
import com.example.kitty.utils.GeoJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @JsonDeserialize(using = GeoJsonDeserializer.class)
    private GeoJsonPoint location;
    private List<Attribute> attributes;
    private String subCategory;
    private String description;
}
