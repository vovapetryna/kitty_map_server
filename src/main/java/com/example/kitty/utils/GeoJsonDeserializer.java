package com.example.kitty.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.io.IOException;

public class GeoJsonDeserializer extends JsonDeserializer<GeoJsonPoint> {
    @Override
    public GeoJsonPoint deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        final JsonNode tree = jp.getCodec().readTree(jp);
        System.out.println(tree);
        final double x = tree.get("x").asDouble();
        final double y = tree.get("y").asDouble();

        return new GeoJsonPoint(x, y);
    }
}