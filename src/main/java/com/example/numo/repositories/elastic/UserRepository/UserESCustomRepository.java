package com.example.numo.repositories.elastic.UserRepository;

import com.example.numo.dto.Bucket;
import com.example.numo.entities.elastic.Group;
import com.example.numo.entities.elastic.UserES;

import java.util.List;
import java.util.Map;

public interface UserESCustomRepository {
    List<UserES> findAllFromGroup(Group group);

    Map<String, List<Bucket>> aggregateSubscribeUnsubscribeStatByGroup(Group group);

    Map<String, List<Bucket>> aggregateUsersBySource(Group group);

    Map<String, List<Bucket>> aggregateActivityByGroup(Group group);

    Map<String, List<Bucket>> aggregateLikesActivityByGroup(Group group);

    Map<String, List<Bucket>> aggregateDislikesActivityByGroup(Group group);

    Map<String, List<Bucket>> aggregateTopEventsByGroup(Group group);
}
