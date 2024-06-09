package com.example.kitty.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Slf4j
public class Path {
    private String id;
    private List<LatLong> path; // shared to frontend
    private List<PgEdge> edges;
    private List<Double> score;

    public Path(List<PgEdge> edges) {
        var orientedEdges = new ArrayList<PgEdge>();
        for (var e : edges) {
            if (orientedEdges.size() == 0) {
                orientedEdges.add(e);
            } else {
                var prevEdge = orientedEdges.get(orientedEdges.size() - 1);
                if (prevEdge.source.equals(e.source)) {
                    prevEdge.swapEdge();
                } else if (prevEdge.target.equals(e.target)) {
                    e.swapEdge();
                } else if (prevEdge.source.equals(e.target)) {
                    prevEdge.swapEdge();
                    e.swapEdge();
                }
                orientedEdges.add(e);
            }
        }

        this.edges = orientedEdges;
        this.id = UUID.randomUUID().toString();
        this.score = null;
        this.path = edges.stream().flatMap(e -> Stream.of(e.start, e.end)).toList();
    }
}
