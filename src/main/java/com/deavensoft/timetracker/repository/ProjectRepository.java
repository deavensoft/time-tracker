package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

  Project findProjectByName(String name);

//  @Query("SELECT p FROM project as p,USER as u,PROJECT_USERS as pu where p.id = pu.PROJECT_ID and pu.USER_ID = u.id and u.id = ?1")
  @Query("select distinct p from Project p LEFT JOIN p.users u where u.id = ?1")
  List<Project> findProjectAndUserById(Long userId);

}
