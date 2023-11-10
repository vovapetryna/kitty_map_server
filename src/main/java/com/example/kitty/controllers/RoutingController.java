package com.example.kitty.controllers;


import com.example.kitty.entities.LatLong;
import com.example.kitty.entities.LatLongPair;
import com.example.kitty.services.GraphHopperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(ControllerAPI.ROUT_CONTROLLER)
@RequiredArgsConstructor
public class RoutingController {
    private final GraphHopperService routingService;

    @PostMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LatLong>> findRoute(@RequestBody LatLongPair latLongPair) {
        return new ResponseEntity<>(routingService.getRouteBetweenPoints(latLongPair).getKey(), HttpStatus.OK);
    }

}
