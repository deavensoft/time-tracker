package com.deavensoft.timetracker.config;

import com.deavensoft.timetracker.repository.JiraProjectRepository;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.JiraUserRepository;
import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import com.deavensoft.timetracker.service.*;
import com.deavensoft.timetracker.service.io.ExcelImporter;
import com.deavensoft.timetracker.service.io.Importer;
import com.deavensoft.timetracker.service.jira.JiraProjectService;
import com.deavensoft.timetracker.service.jira.JiraProjectServiceImpl;
import com.deavensoft.timetracker.service.jira.JiraUserService;
import com.deavensoft.timetracker.service.jira.JiraUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  public WorkLogService workLogService(WorkLogRepository repository, ProjectRepository projectRepository) {
    return new WorkLogServiceImpl(repository, projectRepository);
  }

  @Bean
  public UserService userService(UserRepository repository) {
    return new UserServiceImpl(repository);
  }

  @Bean
  public ProjectService projectService(ProjectRepository projectRepository,
      UserRepository userRepository) {
    return new ProjectServiceImpl(projectRepository, userRepository);
  }

  @Bean
  public JiraUserService jiraUserService(JiraUserRepository jiraUserRepository, UserService userService) {
    return new JiraUserServiceImpl(jiraUserRepository, userService);
  }

  @Bean
  public JiraProjectService jiraProjectService(JiraProjectRepository jiraProjectRepository, ProjectService projectService) {
    return new JiraProjectServiceImpl(jiraProjectRepository, projectService);
  }

  @Bean
  public Importer importer() {
    return new ExcelImporter();
  }

  @Bean
  public ImporterService importerService(Importer importer, JiraUserService jiraUserService,
      JiraProjectService jiraProjectService, WorkLogRepository workLogRepository) {
    return new ImporterServiceImpl(importer, jiraUserService,jiraProjectService,workLogRepository);
  }
}
