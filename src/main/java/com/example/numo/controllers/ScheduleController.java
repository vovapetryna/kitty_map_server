package com.example.numo.controllers;

import com.example.numo.dto.ScheduleDto;
import com.example.numo.entities.elastic.Schedule;
import com.example.numo.services.ScheduleService;
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
@RequestMapping(ControllerAPI.SCHEDULE_CONTROLLER)
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Schedule>> listSchedules() {
        return new ResponseEntity<>(scheduleService.getScheduleList(), HttpStatus.OK);
    }

    @PostMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> create(@RequestBody ScheduleDto dto) {
        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.OK);
    }

    @DeleteMapping(value = ControllerAPI.CONTROLLER_SPECIFIC_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.removeSchedule(id), HttpStatus.OK);
    }

}
