package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.WorkLogMapper;
import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.service.WorkLogService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(WorkLogEndpoint.BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class WorkLogEndpoint {

  public static final String BASE_URL = "/v1.0/worklogs";
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  private final WorkLogService workLogService;
  private final WorkLogMapper mapper;


  @GetMapping("/{id}")
  public WorkLogDto getWorkLog(@PathVariable Long id) {
    return mapper.workLogToWorkLogDto(workLogService.getWorkLogById(id));
  }

  @GetMapping
  public List<WorkLogDto> getWorkLogs() {
    return workLogService.getAllWorkLogs().stream().map(mapper::workLogToWorkLogDto)
        .collect(Collectors.toList());
  }

  @PostMapping
  public WorkLogDto createWorkLog(@RequestBody WorkLogDto workLogDto) {
    if (workLogDto.getUserId() == null) {
      throw new IllegalArgumentException("User id must not be null!");
    } else if (workLogDto.getProjectId() == null) {
      throw new IllegalArgumentException("Project id must not be null!");
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

  @GetMapping("/{from}/{to}")
  public List<WorkLogDto> getWorkLogsBetweenDates(@PathVariable String from,
      @PathVariable String to) {
    LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern(DATE_FORMAT));
    LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern(DATE_FORMAT));

    return workLogService.getWorkLogsBetweenDates(fromDate, toDate).stream()
        .map(mapper::workLogToWorkLogDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/user/{userId}")
  public List<WorkLogDto> getWorkLogsByUser(@PathVariable Long userId) {
    return workLogService.getWorkLogByUser(userId)
        .stream().map(mapper::workLogToWorkLogDto)
        .collect(Collectors.toList());
  }


  @GetMapping("/user/{userId}/{from}/{to}")
  public List<WorkLogDto> getWorkLogsBetweenDatesWithUser(@PathVariable Long userId,
      @PathVariable String from, @PathVariable String to) {
    LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern(DATE_FORMAT));
    LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern(DATE_FORMAT));

    return workLogService.getWorkLogsBetweenDatesWithUser(userId, fromDate, toDate)
        .stream().map(mapper::workLogToWorkLogDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/project/{projectId}/user/{userId}")
  public List<WorkLogDto> getWorkLogsWithProject(@PathVariable Long projectId, @PathVariable Long userId) {
    return workLogService
        .getWorkLogAndProjectByIdAndUserById(projectId,userId).stream()
        .map(mapper::workLogToWorkLogDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/project/{projectId}/{from}/{to}")
  public List<WorkLogDto> getWorkLogsBetweenDatesWithProject(@PathVariable Long projectId,
      @PathVariable String from, @PathVariable String to) {
    LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern(DATE_FORMAT));
    LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern(DATE_FORMAT));

    return workLogService
        .getWorkLogsBetweenDatesWithProject(projectId, fromDate, toDate).stream()
        .map(mapper::workLogToWorkLogDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/{projectId}/{userId}/{from}/{to}")
  public List<WorkLogDto> getWorkLogsBetweenDatesWithProjectAndUser(@PathVariable Long projectId,
      @PathVariable Long userId, @PathVariable String from, @PathVariable String to) {
    LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern(DATE_FORMAT));
    LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern(DATE_FORMAT));

    return workLogService
        .getWorkLogsBetweenDatesWithProjectAndUsers(projectId, userId, fromDate, toDate).stream()
        .map(mapper::workLogToWorkLogDto)
        .collect(Collectors.toList());
  }
}
