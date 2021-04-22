package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.integration.jira.domain.JiraProject;
import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import com.deavensoft.timetracker.integration.jira.domain.JiraWorkLog;
import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.Role.UserRole;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.exception.JiraNotFoundException;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import com.deavensoft.timetracker.service.io.ExcelExtractor;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class JiraImporterServiceImplTest {

  @Mock
  ExcelExtractor excelExtractor;
  @Mock
  JiraUserService jiraUserService;
  @Mock
  JiraProjectService jiraProjectService;
  @Mock
  WorkLogRepository workLogRepository;
  @InjectMocks
  JiraImporterServiceImpl jiraImporterServiceImpl;

  @Captor
  ArgumentCaptor<List<WorkLog>> workArgumentCaptor;

  JiraUser jiraUser1;
  JiraUser jiraUser2;
  JiraProject jiraProject;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    Role role = new Role();
    role.setRole(UserRole.EMPLOYEE);

    User user = new User();
    user.setId(1L);
    user.setFirstName("TestMe");
    user.setLastName("");
    user.setRoles(Arrays.asList(role));

    User user2 = new User();
    user2.setId(2L);
    user2.setFirstName("TestMe2");
    user2.setLastName("");
    user2.setRoles(Arrays.asList(role));

    Project project = new Project();
    project.setName("Test");
    project.getUsers().add(user);
    project.getUsers().add(user2);

    jiraProject = new JiraProject();
    jiraProject.setName("Test");
    jiraProject.setProject(project);

    jiraUser1 = new JiraUser();
    jiraUser1.setName("TestMe");
    jiraUser1.setUser(user);

    jiraUser2 = new JiraUser();
    jiraUser2.setName("TestMe2");
    jiraUser2.setUser(user);

    when(jiraUserService.getJiraUserByName("TestMe")).thenReturn(jiraUser1);
    when(jiraUserService.getJiraUserByName("TestMe2")).thenReturn(jiraUser2);
    when(jiraProjectService.getJiraProjectByName("Test")).thenReturn(jiraProject);

  }

  @Test
  void testRead() throws Exception {
    JiraWorkLog jiraWorkLog = new JiraWorkLog();
    jiraWorkLog.setJiraUser(jiraUser1);
    jiraWorkLog.setJiraProject(jiraProject);
    JiraWorkLog jiraWorkLog2 = new JiraWorkLog();
    jiraWorkLog2.setJiraUser(jiraUser2);
    jiraWorkLog2.setJiraProject(jiraProject);

    when(excelExtractor.extractExcel(any())).thenReturn(new HashMap<String, List<JiraWorkLog>>() {{
      put("TestMe",
          Arrays.<JiraWorkLog>asList(jiraWorkLog));
      put("TestMe2",
          Arrays.<JiraWorkLog>asList(jiraWorkLog2));
    }});

    jiraImporterServiceImpl.importExcel(null);

    verify(workLogRepository).saveAll(workArgumentCaptor.capture());
    List<WorkLog> workLogs = workArgumentCaptor.getValue();

    MatcherAssert.assertThat(workLogs, hasSize(2));
    MatcherAssert.assertThat(workLogs.get(0), is(jiraWorkLog.toWorkLog()));
    MatcherAssert.assertThat(workLogs.get(1), is(jiraWorkLog2.toWorkLog()));
    MatcherAssert.assertThat(workLogs.get(0).getUser(), is(jiraUser1.getUser()));
    MatcherAssert.assertThat(workLogs.get(1).getUser(), is(jiraUser2.getUser()));
    MatcherAssert.assertThat(workLogs.get(1).getUser(), is(workLogs.get(0).getUser()));
  }


  @Test
  public void testRead_shouldRaiseException_WhenJiraUserNotFound() throws IOException {
    JiraWorkLog jiraWorkLog2 = new JiraWorkLog();
    jiraWorkLog2.setJiraUser(jiraUser2);
    jiraWorkLog2.setJiraProject(jiraProject);

    String jiraUserName = "NonExistent";
    when(excelExtractor.extractExcel(any())).thenReturn(new HashMap<String, List<JiraWorkLog>>() {{
      put(jiraUserName,
          Arrays.<JiraWorkLog>asList(jiraWorkLog2));
    }});
     JiraNotFoundException jiraUserNotFoundException = assertThrows(JiraNotFoundException.class, () ->{
      jiraImporterServiceImpl.importExcel(null);
    });
     String expected = "Jira user with name: " + jiraUserName + " doesn't exist. ";

     assertEquals(expected, jiraUserNotFoundException.getMessage());
  }

  @Test
  public void testRead_shouldRaiseException_WhenJiraProjectNotFound() throws IOException {
    JiraWorkLog jiraWorkLog2 = new JiraWorkLog();
    jiraProject.setName("NonExistent");
    jiraWorkLog2.setJiraUser(jiraUser2);
    jiraWorkLog2.setJiraProject(jiraProject);

    String jiraUserName = "TestMe2";
    when(excelExtractor.extractExcel(any())).thenReturn(new HashMap<String, List<JiraWorkLog>>() {{
      put(jiraUserName,
          Arrays.<JiraWorkLog>asList(jiraWorkLog2));
    }});
    JiraNotFoundException jiraUserNotFoundException = assertThrows(JiraNotFoundException.class, () ->{
      jiraImporterServiceImpl.importExcel(null);
    });
    String expected = "Jira project with name: " + jiraWorkLog2.getJiraProject().getName() + " doesn't exist. ";
    assertEquals(expected, jiraUserNotFoundException.getMessage());
  }
}
//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme