package com.example.kitty.repositories;

import com.example.kitty.entities.LatLongPair;
import com.example.kitty.entities.Path;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
@Slf4j
public class PgGeneticRepository {
    private final PgRoutingRepository routingRepository;
    private final Random random = new Random();

    private List<Integer> mutatedLengths = new ArrayList<>();

    public Path mutateRoute(Path route) {
        if (route.getEdges().size() < 10) {
            return route;
        }
        var minMutationLen = 5;
        var mutationRegionStart = random.nextInt(route.getEdges().size() - minMutationLen - 2) + 1;
        var mutationRegionLength = random.nextInt(route.getEdges().size() - mutationRegionStart - minMutationLen - 1) + minMutationLen;
        var mutationLatLongPair = new LatLongPair(
                route.getEdges().get(mutationRegionStart).start,
                route.getEdges().get(mutationRegionStart + mutationRegionLength).end);

        var options = routingRepository.getKPathsKSPDijkstra(mutationLatLongPair, "ways", 10);
        if (options.size() == 0) {
            return route;
        }
        var mutated = options.get(random.nextInt(options.size()));

        return new Path(Stream.of(
                route.getEdges().stream().limit(mutationRegionStart),
                mutated.getEdges().stream(),
                route.getEdges().stream().skip(mutationRegionStart + mutationRegionLength + 1)
        ).flatMap(s -> s).toList());
    }

    public Path crossoverRoutes(Path left, Path right) {
        var crossoverPoint = random.nextInt(left.getEdges().size() - 3) + 1;
        var crossoverLeftPoint = left.getEdges().get(crossoverPoint);
        var crossoverRightPoint = routingRepository.getEdgeWithClosestStartInRoute(crossoverLeftPoint.start, right);
        var junctionLatLongPair = new LatLongPair(crossoverLeftPoint.start, crossoverRightPoint.getSecond().start);

        var junction = new Path(List.of(crossoverLeftPoint));
        if (!zeroDistance(junctionLatLongPair)) {
            var options = routingRepository.getKPathsKSPDijkstra(junctionLatLongPair, "ways", 10);
            junction = options.get(random.nextInt(options.size()));
        }

        return new Path(Stream.of(
                left.getEdges().stream().limit(crossoverPoint),
                junction.getEdges().stream(),
                right.getEdges().stream().skip(crossoverRightPoint.getFirst() + 1)
        ).flatMap(s -> s).toList());
    }

    private Boolean zeroDistance(LatLongPair pair) {
        return Double.compare(pair.getFrom().getLat(), pair.getTo().getLat()) == 0
                && Double.compare(pair.getFrom().getLng(), pair.getTo().getLng()) == 0;
    }
}
