package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import java.util.List;

public interface JiraUserService {

  List<JiraUser> getAllJiraUsers();

  JiraUser crateJiraUser(Long id, JiraUser user);

  JiraUser updateJiraUser(Long id, JiraUser user);

  void deleteJiraUser(Long id);

  JiraUser getJiraUserByName(String name);

  JiraUser mapUserToJiraUser(JiraUser jiraUser);

}
