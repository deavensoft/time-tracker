package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import org.springframework.data.repository.CrudRepository;

public interface JiraUserRepository extends CrudRepository<JiraUser, Long> {
  JiraUser findByName(String name);

}
