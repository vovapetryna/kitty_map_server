package com.example.kitty.controllers;

import com.example.kitty.entities.LatLongPair;
import com.example.kitty.entities.Path;
import com.example.kitty.services.PgRoutingService;
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
    private final PgRoutingService pgRoutingService;

    @PostMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Path>> findRoute(@RequestBody LatLongPair latLongPair) {
        return new ResponseEntity<>(pgRoutingService.getOptimalRoute(latLongPair, false), HttpStatus.OK);
    }

}
