package com.deavensoft.timetracker.api.mapper;


import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {User.class, Project.class})
public interface WorkLogMapper {
    WorkLogMapper INSTANCE = Mappers.getMapper(WorkLogMapper.class);

    @Mapping(target="userId", source = "user.id")
    @Mapping(target="projectId", source = "project.id")
    WorkLogDto workLogToWorkLogDto(WorkLog workLog);

    @Mapping(target = "user.id", source="userId")
    @Mapping(target = "project.id", source="projectId")
    WorkLog workLogDtoToWorkLog(WorkLogDto workLogDto);

}
