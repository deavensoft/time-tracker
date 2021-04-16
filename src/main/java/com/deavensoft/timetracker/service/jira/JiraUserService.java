package com.deavensoft.timetracker.service.jira;

import com.deavensoft.timetracker.domain.jira.JiraUser;

public interface JiraUserService {

  JiraUser crateJiraUser(Long id,JiraUser user);

  JiraUser getJiraUserByName(String name);

}
