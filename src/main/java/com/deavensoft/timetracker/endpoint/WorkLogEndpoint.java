package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.WorkLogMapper;
import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(WorkLogEndpoint.BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RestController
public class WorkLogEndpoint {

    public static final String BASE_URL = "/v1.0/worklogs";
    private final WorkLogService workLogService;
    private final WorkLogMapper mapper;

    @GetMapping("/{id}")
    public WorkLogDto getWorkLog(@PathVariable Long id) {
        return mapper.workLogToWorkLogDto(workLogService.getWorkLogById(id));
    }

    @GetMapping
    public List<WorkLogDto> getWorkLogs() {
        return workLogService.getAllWorkLogs().stream().map(mapper::workLogToWorkLogDto).collect(Collectors.toList());
    }

    @PostMapping
    public WorkLogDto createWorkLog(@RequestBody WorkLogDto workLogDto) {
        if (workLogDto.getUserId() == null) {
            throw new IllegalArgumentException("User id must not be null!");
        } else {

            WorkLog workLog = mapper.workLogDtoToWorkLog(workLogDto);
            WorkLog returnedWorkLog = workLogService.createWorkLog(workLog);

            return mapper.workLogToWorkLogDto(returnedWorkLog);
        }

    }

    @PutMapping({"/{id}"})
    public WorkLogDto updateWorkLog(@PathVariable Long id, @RequestBody WorkLogDto workLogDto) {
        WorkLog workLog = mapper.workLogDtoToWorkLog(workLogDto);

        return mapper.workLogToWorkLogDto(workLogService.updateWorkLog(id, workLog));
    }

    @DeleteMapping("/{id}")
    public void deleteWorkLog(@PathVariable Long id) {
        workLogService.deleteWorkLog(id);
    }
}
