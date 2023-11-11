package com.example.kitty.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(ControllerAPI.PING_CONTROLLER)
@RequiredArgsConstructor
public class PingController {
    @GetMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateGraphCache() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}

