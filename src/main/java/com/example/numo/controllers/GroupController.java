package com.example.numo.controllers;

import com.example.numo.dto.GroupDto;
import com.example.numo.entities.elastic.Group;
import com.example.numo.services.GroupService;
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
@RequestMapping(ControllerAPI.GROUP_CONTROLLER)
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Group>> listGroups() {
        return new ResponseEntity<>(groupService.getGroupList(), HttpStatus.OK);
    }

    @GetMapping(value = ControllerAPI.CONTROLLER_SPECIFIC_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> getById(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.getGroupById(id), HttpStatus.OK);
    }

    @PostMapping(value = ControllerAPI.CONTROLLER_GENERAL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> create(@RequestBody GroupDto dto) {
        return new ResponseEntity<>(groupService.createGroup(dto), HttpStatus.OK);
    }

    @DeleteMapping(value = ControllerAPI.CONTROLLER_SPECIFIC_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.removeGroup(id), HttpStatus.OK);
    }

}
