package com.example.numo.repositories.elastic.UserRepository;

import com.example.numo.entities.elastic.UserES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserESRepository extends ElasticsearchRepository<UserES, Long>, UserESCustomRepository {

    List<UserES> findAll();
}
