package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.integration.jira.domain.JiraProject;
import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.repository.JiraProjectRepository;
import com.deavensoft.timetracker.service.ProjectService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JiraProjectServiceImpl implements JiraProjectService {

  private final JiraProjectRepository jiraProjectRepository;
  private final ProjectService projectService;


  @Override
  public JiraProject getJiraProjectByName(String name) {
    return jiraProjectRepository.findByName(name);
  }

  @Override
  public JiraProject crateJiraProject(Long id, JiraProject jiraProject) {
    Project project = null;
    try{
      project = projectService.getProjectById(id);
    }catch (IllegalArgumentException e){
      project = createProjectWithJiraProjectName(jiraProject);
    }
    jiraProject.setProject(project);
    return jiraProjectRepository.save(jiraProject);
  }

  private Project createProjectWithJiraProjectName(JiraProject jiraProject){
    Project project = new Project();
    project.setName(jiraProject.getName());
    return projectService.createProject(project);
  }
}
