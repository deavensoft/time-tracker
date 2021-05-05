package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

  Project findProjectByName(String name);

  Project findProjectByIdAndUsers(Long id, User userId);

}
