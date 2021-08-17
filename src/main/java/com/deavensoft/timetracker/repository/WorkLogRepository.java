package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.WorkLog;
import java.time.LocalDate;
import java.util.List;
import org.apache.tomcat.jni.Local;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WorkLogRepository extends CrudRepository<WorkLog, Long> {

  @Query("select w from WorkLog w where w.user.id = ?1")
  List<WorkLog> findWorkLogAndUserById(Long userId);

  @Query("select w from WorkLog w where w.project.id = ?1 and w.user.id = ?2")
  List<WorkLog> findWorkLogAndProjectByIdAndUserById(Long projectId, Long userId);

  @Query("select distinct e from WorkLog e where e.date between ?1 and ?2 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDates(LocalDate from, LocalDate to);

  @Query("select distinct e from WorkLog e,User u where e.user.id = ?1 and e.date between ?2 and ?3 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDatesWithUser(Long userId ,LocalDate from, LocalDate to);

  @Query("select distinct e from WorkLog e,Project u where e.project.id = ?1 and e.date between ?2 and ?3 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDatesWithProject(Long projectId ,LocalDate from, LocalDate to);

  @Query("select distinct e from WorkLog e,Project u where e.project.id = ?1 and e.user.id = ?2 and e.date between ?3 and ?4 order by e.date ")
  List<WorkLog> findWorkLogsBetweenDatesWithProjectAndUser(Long projectId, Long userId ,LocalDate from, LocalDate to);
}

