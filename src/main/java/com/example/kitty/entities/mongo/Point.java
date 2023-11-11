package com.example.kitty.entities.mongo;

import com.example.kitty.entities.enums.Category;
import com.example.kitty.utils.GeoJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("points")
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
public class Point {
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String name;
    @Indexed
    private Category category;
    private List<Attribute> attributes;
    private String subCategory;
    private String description;

    // internally used attributes (export from osm)
    private String amenity;
    private String wheelchair;
    private Boolean bus_stop;
    private String shelter;

    private Boolean wifi;
    private Boolean hasToilets;
    private String hasToiletWheelchair;
    private Boolean steps;
    private Boolean ramp;

    private Boolean wasEditedRamp;
    private Boolean wasEditedObstacle;

    private Long wayId;

    @JsonDeserialize(using = GeoJsonDeserializer.class)
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
}