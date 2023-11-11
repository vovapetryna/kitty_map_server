package com.example.kitty.services;

import com.example.kitty.dto.PointDto;
import com.example.kitty.dto.PointFilterDto;
import com.example.kitty.entities.mongo.Point;
import com.example.kitty.mappers.PointMapper;
import com.example.kitty.repositories.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final PointMapper pointMapper;
    private final IdGenerator idGenerator;

    public List<Point> filterPoints(PointFilterDto pointFilterDto) {
        return pointRepository.findAllPointsByTrainingFilterData(pointFilterDto);
    }

    public Point createPoint(PointDto pointDto) {
        return pointRepository.save(pointMapper.toModel(pointDto).setId(idGenerator.nextId()));
    }

    public Point updatePoint(Point point) {
        if (pointRepository.findById(point.getId()).isPresent()) {
            if (pointRepository.findById(point.getId()).get().getRamp() != point.getRamp()) {
                point.setWasEditedRamp(true);
            }
        }
        return pointRepository.save(point);
    }

    public void deletePoint(Long pointId) {
        pointRepository.deleteById(pointId);
    }

    public List<Point> getPointsList() {
        return pointRepository.findAllTaggedWithCategory();
    }
}
