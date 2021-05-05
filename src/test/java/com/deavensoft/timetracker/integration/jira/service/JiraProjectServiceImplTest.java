package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.Role.UserRole;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.integration.jira.domain.JiraProject;
import com.deavensoft.timetracker.repository.JiraProjectRepository;
import com.deavensoft.timetracker.service.ProjectService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JiraProjectServiceImplTest {

  @Mock
  JiraProjectRepository jiraProjectRepository;
  @Mock
  ProjectService projectService;
  @InjectMocks
  JiraProjectServiceImpl jiraProjectServiceImpl;

  Project project;
  User user;
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    Role role = new Role();
    role.setRole(UserRole.EMPLOYEE);


    project = new Project(1L, "name", "description", Boolean.TRUE, Arrays.<User>asList(new User()));
    project.setName("ProjectTest");

    user = new User();
    user.setId(1L);
    user.setFirstName("TestMe");
    user.setLastName("");
    user.setRoles(Arrays.asList(role));

  }
  @Test
  void testCrateJiraProject() {
    JiraProject jiraProject = new JiraProject();
    jiraProject.setName("Test");
    JiraProject returned = new JiraProject();
    returned.setName("Test");
    returned.setProject(project);

    when(projectService.getProjectById(1L)).thenReturn(project);
    when(jiraProjectRepository.save(any())).thenReturn(returned);

    verify(projectService, never()).createProject(any());
    JiraProject result = jiraProjectServiceImpl.crateJiraProject(1L, jiraProject);
    assertEquals(returned, result);
  }

  @Test
  void testCrateJiraProject_shouldCreateJiraProjectWhenNotFound() {
    JiraProject jiraProject = new JiraProject();
    jiraProject.setName("Test");
    JiraProject returned = new JiraProject();
    returned.setName("Test");
    project.setName(returned.getName());
    returned.setProject(project);

    when(projectService.getProjectById(2L)).thenThrow(IllegalArgumentException.class);
    when(projectService.createProject(any())).thenReturn(project);
    when(jiraProjectRepository.save(any())).thenReturn(returned);

    JiraProject result = jiraProjectServiceImpl.crateJiraProject(2L, jiraProject);
    verify(projectService, atLeastOnce()).createProject(any());
    assertEquals(returned, result);
    assertEquals(returned.getProject().getName(), result.getProject().getName());
    assertEquals(project,result.getProject());
  }
}
