package com.example.numo.services;

import com.example.numo.dto.GroupDto;
import com.example.numo.entities.elastic.Group;
import com.example.numo.mappers.GroupMapper;
import com.example.numo.repositories.elastic.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    private final IdGenerator idGenerator = new IdGenerator();

    public Group createGroup(GroupDto groupDto){
        if (groupRepository.existsByNameIgnoreCase(groupDto.getName())){
            throw new IllegalArgumentException("Group with name " + groupDto.getName() + " already exist");
        }
        return groupRepository.save(groupMapper.toModel(groupDto).setId(idGenerator.nextId()));
    }

    public Group getGroupById(Long id){
        return groupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found group with such id"));
    }

    public GroupDto getGroupDtoById(Long id){
        return groupMapper.toDto(groupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found group with such id")));
    }

    public Group removeGroup(Long id){
        Optional<Group> groupForRemoving = groupRepository.findById(id);
        if (groupForRemoving.isEmpty()){
            throw new IllegalArgumentException("Not found group with such id");
        }
        groupRepository.deleteById(id);
        return groupForRemoving.get();
    }

    public List<Group> getGroupList(){
        return groupRepository.findAll();
    }

}
