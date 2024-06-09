package com.example.kitty.services;

import com.example.kitty.entities.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoringService {
    public List<Path> getParetoFront(List<Path> paths) {
        return paths.stream().filter(p -> {
            for (var otherPath : paths) {
                if (isDominated(p, otherPath)) {
                    return false;
                }
            }
            return true;
        }).toList();
    }

    private boolean isDominated(Path check, Path by) {
        for (int i = 0; i < check.getScore().size(); i++) {
            if (check.getScore().get(i) / by.getScore().get(i) > 1.1) {
                return true;
            }
        }
        return false;
    }

    public List<Double> score(Path path) {
        var length = getLength(path);
        var stepsNumber = getStepsNumber(path);
        var crosses = getRoadCrossesNumber(path);
        return List.of(length, stepsNumber, crosses);
    }

    private Double getLength(Path path) {
        return path.getEdges().stream().map(e -> e.length).reduce(0d, Double::sum);
    }

    private Double getStepsNumber(Path path) {
        return (double) path.getEdges().stream().filter(e -> e.tagId == 122).count();
    }

    private Double getRoadCrossesNumber(Path path) {
        return (double) path.getEdges().stream().filter(e -> e.tagId == 107 || e.tagId == 124 || e.tagId == 125).count();
    }
}
