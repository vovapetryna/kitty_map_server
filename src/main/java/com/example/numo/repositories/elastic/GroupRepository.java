package com.example.numo.repositories.elastic;

import com.example.numo.entities.elastic.Group;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends ElasticsearchRepository<Group, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Group> findById(Long id);

    List<Group> findAll();

}
