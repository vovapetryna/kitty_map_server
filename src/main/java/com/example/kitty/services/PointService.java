package com.example.kitty.services;

import com.example.kitty.dto.PointDto;
import com.example.kitty.entities.mongo.Point;
import com.example.kitty.mappers.PointMapper;
import com.example.kitty.repositories.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final PointMapper groupMapper;

    private final IdGenerator idGenerator = new IdGenerator();

    public Point createPoint(PointDto pointDto) {
        return groupMapper.toModel(pointDto);
    }
}
