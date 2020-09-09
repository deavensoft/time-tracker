package com.deavensoft.timetracker.api.mapper;


import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.WorkLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkLogMapper {
    WorkLogMapper INSTANCE = Mappers.getMapper(WorkLogMapper.class);


    WorkLogDto workLogToWorkLogDto(WorkLog workLog);

    WorkLog workLogDtoToWorkLog(WorkLogDto workLogDto);
}
