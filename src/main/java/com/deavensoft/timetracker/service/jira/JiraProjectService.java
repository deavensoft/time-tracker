package com.deavensoft.timetracker.service.jira;

import com.deavensoft.timetracker.domain.jira.JiraProject;

public interface JiraProjectService {

  JiraProject crateJiraProject(Long id,JiraProject jiraProject);

  JiraProject getJiraProjectByName(String name);

}
