package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.WorkLog;

import java.util.List;

public interface WorkLogService {

    WorkLog getWorkLogById(Long id);

    List<WorkLog> getAllWorkLogs();

    WorkLog createWorkLog(WorkLog workLog);

    WorkLog updateWorkLog(Long id, WorkLog workLog);

    void deleteWorkLog(Long id);
}
