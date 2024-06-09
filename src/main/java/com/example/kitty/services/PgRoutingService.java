package com.example.kitty.services;

import com.example.kitty.entities.LatLongPair;
import com.example.kitty.entities.Path;
import com.example.kitty.repositories.PgGeneticRepository;
import com.example.kitty.repositories.PgRoutingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PgRoutingService {
    private final PgRoutingRepository routingRepository;
    private final PgGeneticRepository geneticRepository;
    private final ScoringService scoringService;
    private final List<String> waysTables = List.of(
            "ways", "ways_no_roads", "ways_no_steps",
            "ways_no_dangerous_crossings", "ways_good_foot_walks",
            "ways_no_bad_foot_walks");

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final Random random = new Random();

    public List<Path> getOptimalRoute(LatLongPair points, Boolean doFull) {
        if (doFull) {
            var paths = getInitialSuboptimalPaths(points, 5);
            var iterations = 0;
            List<Path> paretoFront = new ArrayList<>();
            while (iterations < 10) {
                log.info("paretoFront size: {}, iteration: {}, items: {}", paretoFront.size(), iterations, paths.size());

                var mutatedPaths = generateMutatedPaths(paths, Math.min(paths.size() / 10, 20));
                paths.addAll(mutatedPaths);

                var crossoverPaths = generateCrossoverPaths(paths, Math.min(paths.size() / 10, 20));
                paths.addAll(crossoverPaths);

                paths.forEach(p -> {
                    if (p.getScore() == null) {
                        p.setScore(scoringService.score(p));
                    }
                });

                paretoFront = scoringService.getParetoFront(paths);
                iterations++;
            }
            return paretoFront.stream().limit(2).toList();
        }

        return getOptimalPaths(points);
    }

    private List<Path> getInitialSuboptimalPaths(LatLongPair points, Integer scale) {
        var tasks = waysTables.stream()
                .map(table -> CompletableFuture.supplyAsync(() -> routingRepository.getKPathsKSPDijkstra(points, table, scale), executor))
                .toList();
        return new ArrayList<>(tasks.stream().map(CompletableFuture::join).flatMap(Collection::stream).toList());
    }

    private List<Path> generateMutatedPaths(List<Path> sources, Integer count) {
        var tasks = IntStream.range(0, count)
                .mapToObj($ -> sources.get(random.nextInt(sources.size())))
                .map(r -> CompletableFuture.supplyAsync(() -> geneticRepository.mutateRoute(r), executor)).toList();
        return tasks.stream().map(CompletableFuture::join).toList();
    }

    private List<Path> generateCrossoverPaths(List<Path> sources, Integer count) {
        var tasks = IntStream.range(0, count)
                .mapToObj($ -> Pair.of(
                        sources.get(random.nextInt(sources.size())),
                        sources.get(random.nextInt(sources.size()))
                ))
                .map(pair -> CompletableFuture.supplyAsync(() -> geneticRepository.crossoverRoutes(pair.getLeft(), pair.getRight()), executor))
                .toList();
        return tasks.stream().map(CompletableFuture::join).toList();
    }

    public List<Path> getExperimentData(LatLongPair points) {
        var basePathA = routingRepository.getPathDijkstra(points, "ways");
        var basePathB = routingRepository.getPathDijkstra(points, "ways_good_foot_walks");

        var results = new ArrayList<Path>();
        for (int i = 0; i < 9; i++) {
            results.add(geneticRepository.crossoverRoutes(basePathA, basePathB));
        }
        log.info("done");
        return results;
    }

    private List<Path> getOptimalPaths(LatLongPair points) {
        return routingRepository.getKPathsKSPDijkstra(points, "optimal_ways", 2);
    }
}
