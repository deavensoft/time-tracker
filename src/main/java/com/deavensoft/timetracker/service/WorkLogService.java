package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.WorkLog;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.jdbc.Work;

public interface WorkLogService {

  WorkLog getWorkLogById(Long id);

  List<WorkLog> getWorkLogByUser(Long userId);

  List<WorkLog> getAllWorkLogs();

  WorkLog createWorkLog(WorkLog workLog);

  WorkLog updateWorkLog(Long id, WorkLog workLog);

  void deleteWorkLog(Long id);

  List<WorkLog> getWorkLogAndProjectByIdAndUserById(Long projectId,Long userId);

  List<WorkLog> getWorkLogsBetweenDates(LocalDate from, LocalDate to);

  List<WorkLog> getWorkLogsBetweenDatesWithUser(Long userId, LocalDate from, LocalDate to);

  List<WorkLog> getWorkLogsBetweenDatesWithProject(Long projectId, LocalDate from, LocalDate to);

  List<WorkLog> getWorkLogsBetweenDatesWithProjectAndUsers(Long projectId,Long userId, LocalDate from, LocalDate to);

}
