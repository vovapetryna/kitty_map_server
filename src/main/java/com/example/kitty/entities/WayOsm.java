package com.example.kitty.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = WayOsm.COLLECTION_NAME)
@TypeAlias(WayOsm.COLLECTION_NAME)
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class WayOsm {

    public static final String COLLECTION_NAME = "wayOsm";

    @Id
    private Long id;
    private Long relatedObjectId;
//    private Collection<Tag> tags;
    private Map<String, String> tags;
    private List<Long> nodeIds;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonLineString wayline;
    //document id from where data is parsing
    private Long documentId;
}