package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.model.WorkLog;

import com.deavensoft.timetracker.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository workLogRepository;

    @Override
    public Optional<WorkLog> getWorkLogById(Long id) {
        return workLogRepository.findById(id);
    }

    @Override
    public Iterable<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }

    @Override
    public WorkLog createWorkLog(WorkLog workLog) {
        if (workLog.getDate() == null) {
            throw new IllegalArgumentException();
        }

        workLogRepository.save(workLog);
        return workLog;
    }

    @Override
    public void updateWorkLog(WorkLog workLog) {
        if (workLogRepository.findById(workLog.getId()).isPresent())
            workLogRepository.save(workLog);
    }

    @Override
    public void deleteWorkLog(Long id) {
        if (workLogRepository.findById(id).isPresent())
            workLogRepository.deleteById(id);
    }
}
