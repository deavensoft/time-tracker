package com.deavensoft.timetracker.service.jira;

import com.deavensoft.timetracker.domain.jira.JiraProject;
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
      project = new Project();
      project.setName(jiraProject.getName());
      project = projectService.createProject(project);
    }
    jiraProject.setProject(project);
    return jiraProjectRepository.save(jiraProject);
  }
}
