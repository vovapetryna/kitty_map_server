package com.example.kitty.entities.mongo;

import com.example.kitty.entities.enums.AttributeType;
import com.example.kitty.entities.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.w3c.dom.Attr;

import java.util.List;

@Document("raw_points")
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Builder
public class RawPoint {
    @Id
    private Long id;
    private String userDataJson;
    private String category;
    private Category readyCategory;
    private List<String> attributes;
    private List<AttributeType> filteredAttributes;
    private List<Attribute> readyAttributes;
    private String name;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
}