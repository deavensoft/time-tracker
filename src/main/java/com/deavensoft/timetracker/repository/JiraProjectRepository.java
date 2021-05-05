package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.integration.jira.domain.JiraProject;
import org.springframework.data.repository.CrudRepository;

public interface JiraProjectRepository extends CrudRepository<JiraProject, Long> {

  JiraProject findByName(String name);


}
