package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.WorkLog;
import java.time.LocalDate;
import java.util.List;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WorkLogRepository extends CrudRepository<WorkLog, Long> {

  @Query("select e from WorkLog e where e.date between ?1 and ?2 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDates(LocalDate from, LocalDate to);

  @Query("select e from WorkLog e,User u where e.user_id = ?1 and e.date between ?2 and ?3 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDatesWithUser(Long userId ,LocalDate from, LocalDate to);

  @Query("select e from WorkLog e,Project u where e.project_id = ?1 and e.date between ?2 and ?3 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDatesWithProject(Long projectId ,LocalDate from, LocalDate to);

  @Query("select e from WorkLog e,Project u where e.project_id = ?1 and e.user_id= ?2 and e.date between ?3 and ?4 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDatesWithProjectAndUser(Long projectId, Long userId ,LocalDate from, LocalDate to);
}

