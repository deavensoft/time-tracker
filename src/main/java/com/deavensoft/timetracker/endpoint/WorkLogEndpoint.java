package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.api.model.WorkLogListDto;
import com.deavensoft.timetracker.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(WorkLogEndpoint.BASE_URL)
@RequiredArgsConstructor
@RestController
public class WorkLogEndpoint {

    public static final String BASE_URL = "/v1.0/worklogs";
    private final WorkLogService workLogService;

    @GetMapping("/{id}")
    public WorkLogDto getWorkLog(@PathVariable(value = "id") Long id) {
        return workLogService.getWorkLogById(id);
    }

    @GetMapping
    public WorkLogListDto getWorkLogs() {
        return new WorkLogListDto(workLogService.getAllWorkLogs());
    }

    @PostMapping
    public WorkLogDto createWorkLog(@RequestBody WorkLogDto workLogDto) {
        return workLogService.createWorkLog(workLogDto);
    }

    @PutMapping({"/{id}"})
    public WorkLogDto updateWorkLog(@PathVariable Long id, @RequestBody WorkLogDto workLogDto) {
        return workLogService.updateWorkLog(id, workLogDto);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkLog(@PathVariable Long id) {
        workLogService.deleteWorkLog(id);
    }
}
