package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.api.model.WorkLogDto;

import java.util.List;

public interface WorkLogService {

    WorkLogDto getWorkLogById(Long id);

    List<WorkLogDto> getAllWorkLogs();

    WorkLogDto createWorkLog(WorkLogDto workLog);

    WorkLogDto updateWorkLog(Long id, WorkLogDto workLog);

    void deleteWorkLog(Long id);
}
