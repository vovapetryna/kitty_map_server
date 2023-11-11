package com.example.kitty.dto;

import com.example.kitty.entities.LatLong;
import com.example.kitty.entities.enums.Category;
import com.example.kitty.entities.mongo.Attribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointDto {
    private String name;
    private Category category;
    private LatLong location;
    private List<Attribute> attributes;
    private String subCategory;
    private String description;
    private Boolean wifi;
    private Boolean hasToilets;
    private String hasToiletWheelchair;
    private Boolean steps;
    private Boolean ramp;

    private Boolean wasEditedRamp = false;
    private Long wayId;
}
