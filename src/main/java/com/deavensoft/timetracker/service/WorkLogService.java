package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.model.WorkLog;

import java.util.Optional;

public interface WorkLogService {

    Optional<WorkLog> getWorkLogById(Long id);

    Iterable<WorkLog> getAllWorkLogs();

    WorkLog createWorkLog(WorkLog workLog);

    void updateWorkLog(WorkLog workLog);

    void deleteWorkLog(Long id);
}
