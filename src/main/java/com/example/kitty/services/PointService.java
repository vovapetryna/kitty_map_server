package com.example.kitty.services;

import com.example.kitty.dto.PointDto;
import com.example.kitty.dto.PointFilterDto;
import com.example.kitty.entities.WayOsm;
import com.example.kitty.entities.enums.AttributeType;
import com.example.kitty.entities.mongo.Attribute;
import com.example.kitty.entities.mongo.Point;
import com.example.kitty.mappers.PointMapper;
import com.example.kitty.repositories.PgRoutingRepository;
import com.example.kitty.repositories.PgWeightingRepository;
import com.example.kitty.repositories.PointRepository;
import com.example.kitty.repositories.WayOsmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final WayOsmRepository wayRepository;
    private final PointMapper pointMapper;
    private final IdGenerator idGenerator;
    private final PgRoutingRepository routingRepository;
    private final PgWeightingRepository weightingRepository;

    public List<Point> filterPoints(PointFilterDto pointFilterDto) {
        return pointRepository.findAllPointsByTrainingFilterData(pointFilterDto);
    }

    public Point createPoint(PointDto pointDto) {
        Double ISSUE_MULTIPLICATION = 10d;
        Double INFRA_MULTIPLICATION = 0.1d; 

        Point point = pointMapper.toModel(pointDto).setId(idGenerator.nextId());

        point.getAttributes().stream().map(a -> {
            Optional<Double> result = Optional.empty();
            if (a.getAttributeType() == AttributeType.issue) {
                result = Optional.of(ISSUE_MULTIPLICATION);
            } else if (a.getAttributeType() == AttributeType.infra) {
                result = Optional.of(INFRA_MULTIPLICATION);
            }
            return result;
        }).filter(Optional::isPresent).flatMap(Optional::stream).findFirst().ifPresent(multiplication -> {
            log.info("adding way point with multiplication: {}", multiplication);
            var pgId = routingRepository.getClosestEdgeToPoint(point.getLatLong());
            log.info("found nearest edge: {}", pgId);
            var pgEdge = weightingRepository.applyReweighing(pgId, multiplication, false);
            point.setWayEdge(pgEdge);
            point.setWayId(pgId);
            point.setWayChangeMultiplication(multiplication);
        });

        return pointRepository.save(point);
    }

    public void deletePoint(Long pointId) {
        pointRepository.findById(pointId).ifPresent(point -> {
            if (point.getWayId() != null && point.getWayChangeMultiplication() != null) {
                weightingRepository.applyReweighing(point.getWayId(), point.getWayChangeMultiplication(), true);
            }
            pointRepository.deleteById(pointId);
        });
    }

    public List<Point> getPointsList() {
        return pointRepository.findAllTaggedWithCategory();
    }
}
