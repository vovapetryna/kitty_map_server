package com.example.numo.mappers;

import com.example.numo.dto.ScheduleDto;
import com.example.numo.entities.elastic.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule toModel(ScheduleDto scheduleDto);

    ScheduleDto toDto(Schedule schedule);

}

