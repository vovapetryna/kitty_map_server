package com.example.kitty.repositories;

import com.example.kitty.dto.PointFilterDto;
import com.example.kitty.entities.mongo.Point;

import java.util.List;

public interface PointCustomRepository {
    List<Point> findAllPointsByTrainingFilterData(PointFilterDto pointFilterData);
}
