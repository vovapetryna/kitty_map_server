package com.example.numo.mappers;

import com.example.numo.dto.GroupDto;
import com.example.numo.entities.elastic.Group;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-21T14:30:17+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public Group toModel(GroupDto groupDto) {
        if ( groupDto == null ) {
            return null;
        }

        Group group = new Group();

        group.setName( groupDto.getName() );
        group.setOrigin( groupDto.getOrigin() );
        group.setAge( groupDto.getAge() );
        group.setLocation( groupDto.getLocation() );
        group.setFrequency( groupDto.getFrequency() );
        group.setNumOfChildren( groupDto.getNumOfChildren() );
        group.setLikesMoreThan( groupDto.getLikesMoreThan() );
        group.setDislikesMoreThan( groupDto.getDislikesMoreThan() );
        group.setHasFinishedRegisterForm( groupDto.getHasFinishedRegisterForm() );
        group.setActivityLevel( groupDto.getActivityLevel() );
        group.setRegisteredFrom( groupDto.getRegisteredFrom() );
        group.setRegisteredTo( groupDto.getRegisteredTo() );

        return group;
    }

    @Override
    public GroupDto toDto(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setName( group.getName() );
        groupDto.setOrigin( group.getOrigin() );
        groupDto.setAge( group.getAge() );
        groupDto.setLocation( group.getLocation() );
        groupDto.setFrequency( group.getFrequency() );
        groupDto.setNumOfChildren( group.getNumOfChildren() );
        groupDto.setLikesMoreThan( group.getLikesMoreThan() );
        groupDto.setDislikesMoreThan( group.getDislikesMoreThan() );
        groupDto.setHasFinishedRegisterForm( group.getHasFinishedRegisterForm() );
        groupDto.setActivityLevel( group.getActivityLevel() );
        groupDto.setRegisteredFrom( group.getRegisteredFrom() );
        groupDto.setRegisteredTo( group.getRegisteredTo() );

        return groupDto;
    }
}
