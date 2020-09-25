package com.deavensoft.timetracker.api.mapper;


import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {User.class})
public interface WorkLogMapper {
    WorkLogMapper INSTANCE = Mappers.getMapper(WorkLogMapper.class);

    @Mapping(target="userId", source = "user.id")
    WorkLogDto workLogToWorkLogDto(WorkLog workLog);

    @Mapping(target = "user.id", source="userId")
    WorkLog workLogDtoToWorkLog(WorkLogDto workLogDto);
}
