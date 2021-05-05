package com.deavensoft.timetracker.config;

import com.deavensoft.timetracker.integration.jira.service.JiraImporterService;
import com.deavensoft.timetracker.integration.jira.service.JiraImporterServiceImpl;
import com.deavensoft.timetracker.repository.JiraProjectRepository;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.JiraUserRepository;
import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import com.deavensoft.timetracker.service.*;
import com.deavensoft.timetracker.integration.jira.service.io.JiraExcelExtractor;
import com.deavensoft.timetracker.service.io.ExcelExtractor;
import com.deavensoft.timetracker.integration.jira.service.JiraProjectService;
import com.deavensoft.timetracker.integration.jira.service.JiraProjectServiceImpl;
import com.deavensoft.timetracker.integration.jira.service.JiraUserService;
import com.deavensoft.timetracker.integration.jira.service.JiraUserServiceImpl;
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
  public ExcelExtractor importer() {
    return new JiraExcelExtractor();
  }

  @Bean
  public JiraImporterService importerService(ExcelExtractor excelExtractor, JiraUserService jiraUserService,
      JiraProjectService jiraProjectService, WorkLogRepository workLogRepository) {
    return new JiraImporterServiceImpl(excelExtractor, jiraUserService,jiraProjectService,workLogRepository);
  }
}
