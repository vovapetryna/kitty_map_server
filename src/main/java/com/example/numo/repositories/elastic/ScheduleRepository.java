package com.example.numo.repositories.elastic;

import com.example.numo.entities.elastic.Schedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ScheduleRepository extends ElasticsearchRepository<Schedule, Long> {
    List<Schedule> findAll();
}
