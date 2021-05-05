package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.integration.jira.domain.JiraProject;
import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import com.deavensoft.timetracker.integration.jira.domain.JiraWorkLog;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.exception.JiraNotFoundException;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import com.deavensoft.timetracker.service.io.ExcelExtractor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class JiraImporterServiceImpl implements JiraImporterService {

  private final ExcelExtractor excelExtractor;
  private final JiraUserService jiraUserService;
  private final JiraProjectService jiraProjectService;
  private final WorkLogRepository workLogRepository;


  @Override
  public void importExcel(InputStream inputStream)throws JiraNotFoundException {
    Map<String, List<JiraWorkLog>> listMap = excelExtractor.extractExcel(inputStream);
    List<String> listOfErrors = validate(listMap);
    if (!listOfErrors.isEmpty()) {
      throw new JiraNotFoundException(listOfErrors.stream().reduce((s, s2) -> s + s2).get());
    }
    List<WorkLog> listWorkLog = processWorkLogs(listMap);
    workLogRepository.saveAll(listWorkLog);
  }



  private List<String> validate(Map<String, List<JiraWorkLog>> listMap) {
    List<String> listOfErrors = new ArrayList<>();
    JiraUser user = new JiraUser();
    user.setName("");
    for (Entry<String, List<JiraWorkLog>> e : listMap.entrySet()) {
      if (jiraUserService.getJiraUserByName(e.getKey()) == null) {
        listOfErrors.add("Jira user with name: " + e.getKey() + " doesn't exist. ");
      }
      JiraProject jiraProject = new JiraProject();
      for (JiraWorkLog jiraWorkLog : e.getValue()) {
        if (!jiraProject.getName().equals(jiraWorkLog.getJiraProject().getName())) {
          jiraProject = jiraProjectService.getJiraProjectByName(jiraWorkLog.getJiraProject().getName());
        }
        if (jiraProject == null) {
          listOfErrors.add("Jira project with name: " + jiraWorkLog.getJiraProject().getName() + " doesn't exist. ");
        }
      }
    }
    return listOfErrors;
  }

  private List<WorkLog> processWorkLogs(Map<String, List<JiraWorkLog>> listMap)
      throws JiraNotFoundException {
    List<WorkLog> listWorkLog = new ArrayList<>();
    for (Entry<String, List<JiraWorkLog>> entry : listMap.entrySet()) {
      String key = entry.getKey();
      JiraUser jiraUser = jiraUserService.getJiraUserByName(key);
      if (jiraUser == null) {
        throw new JiraNotFoundException("Jira user with name: " + key + "not found ");
      }
      JiraProject jiraProject = new JiraProject();
      for (JiraWorkLog workLog : entry.getValue()) {
        if (!jiraProject.getName().equals(workLog.getJiraProject().getName())) {
          jiraProject = jiraProjectService.getJiraProjectByName(workLog.getJiraProject().getName());
        }
        workLog.setJiraUser(jiraUser);
        jiraProject.getProject().getUsers().add(jiraUser.getUser());
        workLog.setJiraProject(jiraProject);
        listWorkLog.add(workLog.toWorkLog());
      }
    }
    return listWorkLog;
  }

}
