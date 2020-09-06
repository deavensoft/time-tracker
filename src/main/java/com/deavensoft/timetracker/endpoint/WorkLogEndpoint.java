package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.model.WorkLog;
import com.deavensoft.timetracker.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/v1.0/worklogs")
@RequiredArgsConstructor
@RestController
public class WorkLogEndpoint {

    private final WorkLogService workLogService;

    @GetMapping("/{id}")
    public Optional<WorkLog> getWorkLog(@PathVariable(value = "id") Long id) {
        return workLogService.getWorkLogById(id);
    }

    @GetMapping
    public Iterable<WorkLog> getWorkLogs() {
        return workLogService.getAllWorkLogs();
    }

    @PostMapping
    public WorkLog createWorkLog(@RequestBody WorkLog workLog) {
        return workLogService.createWorkLog(workLog);
    }

    @PutMapping
    public void updateWorkLog(@RequestBody WorkLog workLog) {
        workLogService.updateWorkLog(workLog);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkLog(@PathVariable(value = "id") Long id) {
        workLogService.deleteWorkLog(id);
    }
}
