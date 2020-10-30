package com.deavensoft.timetracker.api.mapper;

import com.deavensoft.timetracker.api.model.ProjectDto;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDto projectToProjectDto(Project project);

	Project projectDtoToProject(ProjectDto projectDto);
}
