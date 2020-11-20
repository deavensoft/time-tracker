package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {

  private final WorkLogRepository workLogRepository;
  private final ProjectRepository projectRepository;

  @Override
  public WorkLog getWorkLogById(Long id) {
    return workLogRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("Work log with ID = " + id + " does not exist!"));
  }

  @Override
  public List<WorkLog> getAllWorkLogs() {
    List<WorkLog> workLogs = new ArrayList<>();
    workLogRepository.findAll().forEach(workLogs::add);

    return workLogs;
  }

  @Override
  public WorkLog createWorkLog(WorkLog workLog) {
    if (valid(workLog)) {
      return workLogRepository.save(workLog);
    } else {
      throw new IllegalArgumentException("Cannot crate worklog! Worklog data not valid");
    }
  }

  private boolean valid(WorkLog workLog) {
    final User user = workLog.getUser();
    final Project project = workLog.getProject();

    final Optional<Project> foundProject = projectRepository.findById(project.getId());

    return foundProject.isPresent() && foundProject.get().getUsers() != null
        && foundProject.get().getUsers().stream().anyMatch(u -> u.getId().equals(user.getId()));
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
