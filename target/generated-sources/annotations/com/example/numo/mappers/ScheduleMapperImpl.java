package com.example.numo.mappers;

import com.example.numo.dto.ScheduleDto;
import com.example.numo.entities.elastic.Schedule;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-21T14:30:17+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class ScheduleMapperImpl implements ScheduleMapper {

    @Override
    public Schedule toModel(ScheduleDto scheduleDto) {
        if ( scheduleDto == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setName( scheduleDto.getName() );
        schedule.setGroupId( scheduleDto.getGroupId() );
        schedule.setCommunicationType( scheduleDto.getCommunicationType() );
        schedule.setScheduledTime( scheduleDto.getScheduledTime() );

        return schedule;
    }

    @Override
    public ScheduleDto toDto(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        ScheduleDto scheduleDto = new ScheduleDto();

        scheduleDto.setName( schedule.getName() );
        scheduleDto.setGroupId( schedule.getGroupId() );
        scheduleDto.setCommunicationType( schedule.getCommunicationType() );
        scheduleDto.setScheduledTime( schedule.getScheduledTime() );

        return scheduleDto;
    }
}
