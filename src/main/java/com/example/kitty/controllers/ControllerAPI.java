package com.example.kitty.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerAPI {
    public static final String CONTROLLER_SPECIFIC_REQUEST = "/{id}";
    public static final String CONTROLLER_GENERAL_REQUEST = "";
    public static final String POINT_CONTROLLER = "/api/v1/points";
    public static final String ROUT_CONTROLLER = "/api/v1/routs";

    public static final String PING_CONTROLLER = "api/v1/ping";
}
