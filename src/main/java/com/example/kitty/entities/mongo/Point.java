package com.example.kitty.entities.mongo;

import com.example.kitty.entities.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("points")
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class Point {
    @Id
    private Long id;
    private String name;
    private Category category;
    private List<Attribute> attributes;
    private String subCategory;
    private String description;

    // internally used attributes (export from osm)
    private String amenity;
    private String wheelchair;
    private Boolean bus_stop;
    private String shelter;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
}