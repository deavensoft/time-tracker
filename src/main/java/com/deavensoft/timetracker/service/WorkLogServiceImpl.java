package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.jdbc.Work;

@AllArgsConstructor
@Transactional
public class WorkLogServiceImpl implements WorkLogService {

  private final WorkLogRepository workLogRepository;
  private final ProjectRepository projectRepository;


  @Override
  public WorkLog getWorkLogById(Long id) {
    return workLogRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("Work log with ID = " + id + " does not exist!"));
  }

  @Override
  public List<WorkLog> getWorkLogByUser(Long userId) {
    return workLogRepository.findWorkLogAndUserById(userId);
  }

  @Override
  public List<WorkLog> getAllWorkLogs() {
    List<WorkLog> workLogs = new ArrayList<>();
    workLogRepository.findAll().forEach(workLogs::add);

    return workLogs;
  }

  @Override
  @Transactional
  public WorkLog createWorkLog(WorkLog workLog) {
    if (valid(workLog)) {
      return workLogRepository.save(workLog);
    } else {
      throw new IllegalArgumentException("Cannot crate worklog! Worklog data not valid");
    }
  }

  @Transactional
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
    if (valid(workLog)) {
      return workLogRepository.save(workLog);
    } else {
      throw new IllegalArgumentException("Cannot update worklog! Worklog data not valid");
    }
  }


  @Override
  public void deleteWorkLog(Long id) {
    workLogRepository.deleteById(id);
  }

  @Override
  public List<WorkLog> getWorkLogAndProjectByIdAndUserById(Long projectId, Long userId) {
    return workLogRepository.findWorkLogAndProjectByIdAndUserById(projectId, userId);
  }

  @Override
  public List<WorkLog> getWorkLogsBetweenDates(LocalDate from, LocalDate to) {
    return workLogRepository.findWorkLogsBetweenDates(from, to);
  }

  @Override
  public List<WorkLog> getWorkLogsBetweenDatesWithUser(Long userId, LocalDate from,
      LocalDate to) {
    return workLogRepository.findWorkLogsBetweenDatesWithUser(userId, from, to);
  }

  @Override
  public List<WorkLog> getWorkLogsBetweenDatesWithProject(Long projectId, LocalDate from,
      LocalDate to) {
    return workLogRepository.findWorkLogsBetweenDatesWithProject(projectId, from, to);
  }

  @Override
  public List<WorkLog> getWorkLogsBetweenDatesWithProjectAndUsers(Long projectId, Long userId,
      LocalDate from, LocalDate to) {
    return workLogRepository
        .findWorkLogsBetweenDatesWithProjectAndUser(projectId, userId, from, to);
  }
}
