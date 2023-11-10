package com.example.kitty.controllers;

import com.example.kitty.dto.PointDto;
import com.example.kitty.entities.mongo.Point;
import com.example.kitty.services.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(ControllerAPI.POINT_CONTROLLER)
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Point> create(@RequestBody PointDto dto) {
        return new ResponseEntity<>(pointService.createPoint(dto), HttpStatus.OK);
    }

    @GetMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Point>> getPointsList() {
        return new ResponseEntity<>(pointService.getPointsList(), HttpStatus.OK);
    }
}
