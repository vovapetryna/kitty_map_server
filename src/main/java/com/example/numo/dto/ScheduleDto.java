package com.example.numo.dto;

import com.example.numo.entities.enums.CommunicationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDto {
    private String Name;
    private Long groupId;
    private CommunicationType communicationType;
    private String scheduledTime;
}
