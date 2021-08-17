package com.deavensoft.timetracker.api.mapper;

import com.deavensoft.timetracker.api.model.JiraUserDto;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {User.class})
public interface JiraUserMapper {
  JiraUserMapper INSTANCE = Mappers.getMapper(JiraUserMapper.class);

  @Mapping(target = "userId", source="user.id")
  @Mapping(target = "userFirstName", source="user.firstName")
  @Mapping(target = "userLastName", source="user.lastName")
  JiraUserDto jiraUserToJiraUserDto(JiraUser user);

  @Mapping(target = "user.id", source="userId")
  @Mapping(target = "user.firstName", source="userFirstName")
  @Mapping(target = "user.lastName", source="userLastName")
  JiraUser jiraUserDtoToJiraUser(JiraUserDto userDto);

}
