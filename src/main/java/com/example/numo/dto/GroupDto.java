package com.example.numo.dto;

import com.example.numo.entities.enums.ActivityLevel;
import com.example.numo.entities.enums.AmountOfAdvices;
import com.example.numo.entities.enums.Origin;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDto {
    private String name;
    private Origin origin;
    private Integer age;
    private String location;
    private AmountOfAdvices frequency;
    private Integer numOfChildren;
    private Integer likesMoreThan;
    private Integer dislikesMoreThan;
    private Boolean hasFinishedRegisterForm;
    private ActivityLevel activityLevel;
    private String registeredFrom;
    private String registeredTo;
}
