package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.integration.jira.domain.JiraUser;

public interface JiraUserService {

  JiraUser crateJiraUser(Long id,JiraUser user);

  JiraUser getJiraUserByName(String name);

}
