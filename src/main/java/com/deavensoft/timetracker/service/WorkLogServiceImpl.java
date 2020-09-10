package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository workLogRepository;

    @Override
    public WorkLog getWorkLogById(Long id) {
        return workLogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Work log with ID = " + id + " does not exist!"));
    }

    @Override
    public List<WorkLog> getAllWorkLogs() {
        List<WorkLog> workLogs = new ArrayList<>();
        workLogRepository.findAll().forEach(workLogs::add);

        return workLogs;
    }

    @Override
    public WorkLog createWorkLog(WorkLog workLog) {
        return workLogRepository.save(workLog);
    }

    @Override
    public WorkLog updateWorkLog(Long id, WorkLog workLog) {
        workLog.setId(id);

        return workLogRepository.save(workLog);
    }

    @Override
    public void deleteWorkLog(Long id) {
        workLogRepository.deleteById(id);
    }
}
