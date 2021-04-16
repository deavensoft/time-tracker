package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.model.request.JiraMappingRequest;
import com.deavensoft.timetracker.domain.jira.JiraProject;
import com.deavensoft.timetracker.domain.jira.JiraUser;
import com.deavensoft.timetracker.service.ImporterService;
import com.deavensoft.timetracker.service.jira.JiraProjectService;
import com.deavensoft.timetracker.service.jira.JiraUserService;
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

@RequestMapping(ImportExcelEndpoint.BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RestController
@Slf4j
public class ImportExcelEndpoint {

  public static final String BASE_URL = "/v1.0/excel";
  private final ImporterService excelImporterService;
  private final JiraUserService jiraUserService;
  private final JiraProjectService jiraProjectService;


  @PostMapping(value = "/map", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addMapping(@RequestBody JiraMappingRequest excelMapping) throws IOException {

    try{
      excelMapping.getUsers().forEach((s, s2) -> {
        JiraUser jiraUser = new JiraUser();
        jiraUser.setName(s);
        jiraUserService.crateJiraUser(s2, jiraUser);
      });
    }catch (Exception e){
      log.error(e.getMessage());
    }
    try{
      excelMapping.getProjects().forEach((s, s2) -> {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setName(s);
        jiraProjectService.crateJiraProject(s2, jiraProject);
      });
    }catch (Exception e){
      log.error(e.getMessage());
    }


  }

  @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
    excelImporterService.importExcel(file.getInputStream());
  }
}
