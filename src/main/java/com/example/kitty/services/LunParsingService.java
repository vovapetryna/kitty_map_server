package com.example.kitty.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.IntStream;

@Service
public class LunParsingService {
    private final int Z_INDEX = 11;

    private void getTilesData() {
        IntStream.range(1196, 1200).forEach(xIndex -> {
            IntStream.range(689, 692).forEach(yIndex -> {

            });
        });
    }

    private void getTileData(int x, int y) {
        try {
            URL url = new URL("https://maps.lunstatic.net/tiles/basemap-r3/%d/%d/%d.pbf".formatted(Z_INDEX, x, y));
            URLConnection connection = url.openConnection();
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
