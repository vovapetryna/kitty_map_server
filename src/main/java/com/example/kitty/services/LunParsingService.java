package com.example.kitty.services;

import com.example.kitty.entities.mongo.RawPoint;
import com.example.kitty.repositories.RawPointRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdtinc.mapbox_vector_tile.adapt.jts.MvtReader;
import com.wdtinc.mapbox_vector_tile.adapt.jts.TagKeyValueMapConverter;
import com.wdtinc.mapbox_vector_tile.adapt.jts.model.JtsMvt;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class LunParsingService {
    private final int Z_INDEX = 11;

    private final IdGenerator idGenerator;

    private final RawPointRepository rawPointRepository;

    public void getTilesData() {
        IntStream.range(1195, 1200).forEach(xIndex -> {
            IntStream.range(689, 693).forEach(yIndex -> {
                getMVTData(xIndex, yIndex);
            });
        });
    }

    public GeoJsonPoint getGeoFromTiles(double zoom, double x, double y) {
        var n = Math.pow(2.0, zoom);
        var lonDeg = x / n * 360.0 - 180.0;
        var latRad = Math.atan(Math.sinh(Math.PI * (1 - 2 * y / n)));
        var latDeg = 180.0 * (latRad / Math.PI);
        return new GeoJsonPoint(latDeg, lonDeg);
    }

    private void getMVTData(int x, int y) {
        GeometryFactory geomFactory = new GeometryFactory();

        try {
            URL url = new URL("https://misto.lun.ua/external-tiles/data/usefulmapv2/%d/%d/%d.pbf".formatted(Z_INDEX, x, y));
            InputStream input = url.openConnection().getInputStream();

            JtsMvt mvt = MvtReader.loadMvt(input, geomFactory, new TagKeyValueMapConverter());
            ObjectMapper objectMapper = new ObjectMapper();

             mvt.getLayers().forEach(layer -> {
                layer.getGeometries().forEach(g -> {
                    LinkedHashMap<String, String> userData = (LinkedHashMap<String, String>)g.getUserData();

                    var topLeft = getGeoFromTiles(Z_INDEX, x, y);
                    var bottomRight = getGeoFromTiles(Z_INDEX, x + 1, y + 1);
                    var tileWidth = bottomRight.getY() - topLeft.getY();
                    var tileHeight = bottomRight.getX() - topLeft.getX();
                    var point = g.getEnvelopeInternal().centre();
                    double lon = topLeft.getY() + (point.x / layer.getExtent()) * tileWidth; // Assuming a tile size of 4096
                    double lat = topLeft.getX() + (point.y / layer.getExtent()) * tileHeight;

                    String jsonData = "";
                    try {
                        jsonData = objectMapper.writeValueAsString(userData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    var rawPoint = RawPoint.builder()
                            .id(idGenerator.nextId())
                            .userDataJson(jsonData)
                            .location(new GeoJsonPoint(lon, lat))
                            .build();
                    rawPointRepository.save(rawPoint);
                });

                System.out.println("number of added points: " + layer.getGeometries().size());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
