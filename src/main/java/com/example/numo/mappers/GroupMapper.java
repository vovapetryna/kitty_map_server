package com.example.numo.mappers;

import com.example.numo.dto.GroupDto;
import com.example.numo.entities.elastic.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    Group toModel(GroupDto groupDto);

    GroupDto toDto(Group group);

}
