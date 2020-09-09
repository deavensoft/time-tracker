package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.api.mapper.WorkLogMapper;
import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.WorkLogRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final WorkLogMapper mapper;

    public WorkLogServiceImpl(WorkLogRepository workLogRepository, WorkLogMapper mapper) {
        this.workLogRepository = workLogRepository;
        this.mapper = mapper;
    }

    @Override
    public WorkLogDto getWorkLogById(Long id) {
        return workLogRepository
                .findById(id)
                .map(mapper::workLogToWorkLogDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<WorkLogDto> getAllWorkLogs() {
        Iterable<WorkLog> iterableWorkLogs = workLogRepository.findAll();

        return StreamSupport
                .stream(iterableWorkLogs.spliterator(), false)
                .map(mapper::workLogToWorkLogDto)
                .collect(Collectors.toList());

    }

    @Override
    public WorkLogDto createWorkLog(WorkLogDto workLogDto) {
        return mapper.workLogToWorkLogDto(workLogRepository.save(mapper.workLogDtoToWorkLog(workLogDto)));
    }

    @Override
    public WorkLogDto updateWorkLog(Long id, WorkLogDto workLogDto) {
        WorkLog workLog = mapper.workLogDtoToWorkLog(workLogDto);
        workLog.setId(id);

        WorkLog savedWorkLog = workLogRepository.save(workLog);

        return mapper.workLogToWorkLogDto(savedWorkLog);
    }

    @Override
    public void deleteWorkLog(Long id) {
        workLogRepository.deleteById(id);
    }
}
