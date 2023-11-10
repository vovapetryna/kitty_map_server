package com.example.numo.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerAPI {
    public static final String CONTROLLER_SPECIFIC_REQUEST = "/{id}";
    public static final String CONTROLLER_GENERAL_REQUEST = "";
    public static final String GROUP_CONTROLLER = "/api/v1/groups";
    public static final String SCHEDULE_CONTROLLER = "/api/v1/schedules";
    public static final String USER_CONTROLLER = "/api/v1/users";
}
