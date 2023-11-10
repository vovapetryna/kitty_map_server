package com.example.numo.services;

import co.elastic.clients.util.DateTime;
import com.example.numo.dto.ScheduleDto;
import com.example.numo.entities.elastic.Schedule;
import com.example.numo.mappers.ScheduleMapper;
import com.example.numo.repositories.elastic.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final IdGenerator idGenerator = new IdGenerator();

    public Schedule createSchedule(ScheduleDto scheduleDto) {
        return scheduleRepository
                .save(scheduleMapper.toModel(scheduleDto).setCreatedAt(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                        .setId(idGenerator.nextId()));
    }

    public Schedule removeSchedule(Long id) {
        Optional<Schedule> scheduleForRemoving = scheduleRepository.findById(id);
        if (scheduleForRemoving.isEmpty()) {
            throw new IllegalArgumentException("Not found schedule with such id");
        }
        scheduleRepository.deleteById(id);
        return scheduleForRemoving.get();
    }

    public List<Schedule> getScheduleList() {
        return scheduleRepository.findAll();
    }

}
