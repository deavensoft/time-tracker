package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.integration.jira.model.request.JiraMappingRequest;
import com.deavensoft.timetracker.integration.jira.domain.JiraProject;
import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import com.deavensoft.timetracker.integration.jira.service.JiraImporterService;
import com.deavensoft.timetracker.integration.jira.service.JiraProjectService;
import com.deavensoft.timetracker.integration.jira.service.JiraUserService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(ImportJiraEndpoint.BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RestController
@Slf4j
public class ImportJiraEndpoint {

  public static final String BASE_URL = "/v1.0/jira";
  private final JiraImporterService excelJiraImporterService;
  private final JiraUserService jiraUserService;
  private final JiraProjectService jiraProjectService;


  @PostMapping(value = "/map", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addMapping(@RequestBody JiraMappingRequest excelMapping) {
    try{
      excelMapping.getUsers().forEach((jiraUserName, userId) -> {
        JiraUser jiraUser = new JiraUser();
        jiraUser.setName(jiraUserName);
        jiraUserService.crateJiraUser(userId, jiraUser);
      });
    }catch (Exception e){
      log.error(e.getMessage());
    }
    try{
      excelMapping.getProjects().forEach((jiraProjectName, projectId) -> {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setName(jiraProjectName);
        jiraProjectService.crateJiraProject(projectId, jiraProject);
      });
    }catch (Exception e){
      log.error(e.getMessage());
    }


  }

  @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
    excelJiraImporterService.importExcel(file.getInputStream());
  }
}
