package com.example.kitty.repositories;

import com.example.kitty.entities.LatLong;
import com.example.kitty.entities.LatLongPair;
import com.example.kitty.entities.Path;
import com.example.kitty.entities.PgEdge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
@Slf4j
public class PgRoutingRepository {
    private final EntityManager entityManager;

    public List<Path> getAlternativeResults(LatLongPair request) {
        return Stream.of("ways", "ways_no_roads", "ways_no_steps", "ways_no_dangerous_crossings", "ways_good_foot_walks", "ways_no_bad_foot_walks")
                .map(table -> getPathDijkstra(request, table))
                .collect(Collectors.toList());
    }

    public Path getPathDijkstra(LatLongPair request, String waysTable) {
        var startNode = getClosestNodeToPoint(request.getFrom());
        var finishNode = getClosestNodeToPoint(request.getTo());
        Query q = entityManager.createNativeQuery("SELECT path_seq, b.gid, b.tag_id, b.source, b.target, b.length, b.x1, b.y1, b.x2, b.y2 " +
                "FROM pgr_dijkstra( 'SELECT gid AS id, source, target, length AS cost FROM " + waysTable + "', :start, :finish, false) a " +
                "INNER JOIN ways b ON a.edge=b.gid");
        q.setParameter("start", startNode);
        q.setParameter("finish", finishNode);
        List<Object[]> results = q.getResultList();

        return new Path(results.stream()
                .sorted(Comparator.comparingLong(r -> (Integer) r[0]))
                .map(r -> new PgEdge(r, 1)).toList());
    }

    public List<Path> getKPathsKSPDijkstra(LatLongPair request, String waysTable, Integer K) {
        var startNode = getClosestNodeToPoint(request.getFrom());
        var finishNode = getClosestNodeToPoint(request.getTo());

        Query q = entityManager.createNativeQuery("SELECT path_id, seq, b.gid, b.tag_id, b.source, b.target, b.length, b.x1, b.y1, b.x2, b.y2 " +
                "FROM pgr_KSP('SELECT gid AS id, source, target, length AS cost, length AS reverse_cost FROM " + waysTable + "', :start, :finish, :K) a " +
                "INNER JOIN ways b ON a.edge=b.gid");

        q.setParameter("start", startNode);
        q.setParameter("finish", finishNode);
        q.setParameter("K", K);
        List<Object[]> results = q.getResultList();

        return results.stream().collect(Collectors.groupingBy(r -> (Integer) r[0]))
                .values().stream()
                .map(edges -> new Path(edges.stream()
                        .sorted(Comparator.comparingLong(r -> (Integer) r[1]))
                        .map(r -> new PgEdge(r, 2)).toList())
                ).toList();
    }

    private Long getClosestNodeToPoint(LatLong point) {
        Query q = entityManager.createNativeQuery("SELECT id FROM ways_vertices_pgr ORDER BY the_geom <-> ST_SetSRID(ST_Point(:x,:y),4326) LIMIT 1", Long.class);
        q.setParameter("x", point.getLng());
        q.setParameter("y", point.getLat());
        return (Long) q.getSingleResult();
    }

    public Long getClosestEdgeToPoint(LatLong point) {
        Query q = entityManager.createNativeQuery("SELECT edge_id FROM pgr_findCloseEdges('SELECT gid as id, the_geom as geom FROM ways', ST_SetSRID(ST_Point(:x,:y),4326), 0.5);", Long.class);
        q.setParameter("x", point.getLng());
        q.setParameter("y", point.getLat());
        return (Long) q.getSingleResult();
    }

    public Pair<Integer, PgEdge> getEdgeWithClosestStartInRoute(LatLong point, Path route) {
        return IntStream.range(0, route.getEdges().size())
                .mapToObj(idx -> Triple.of(idx, route.getEdges().get(idx), distance(point, route.getEdges().get(idx).start)))
                .min(Comparator.comparingDouble(Triple::getRight))
                .map(t -> Pair.of(t.getLeft(), t.getMiddle()))
                .get();
    }

    // can only be used to compare (doesn't correspond to real distance in km)
    // need to be multiplied to earth radius
    private Double distance(LatLong left, LatLong right) {
        double lat1Rad = Math.toRadians(left.getLat());
        double lat2Rad = Math.toRadians(right.getLat());
        double lon1Rad = Math.toRadians(left.getLng());
        double lon2Rad = Math.toRadians(right.getLng());

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        return Math.sqrt(x * x + y * y);
    }

}
