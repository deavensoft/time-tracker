package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.integration.jira.domain.JiraProject;

public interface JiraProjectService {

  JiraProject crateJiraProject(Long id,JiraProject jiraProject);

  JiraProject getJiraProjectByName(String name);

}
