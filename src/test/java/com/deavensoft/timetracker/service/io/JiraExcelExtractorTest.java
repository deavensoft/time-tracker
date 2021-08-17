package com.deavensoft.timetracker.service.io;

import com.deavensoft.timetracker.integration.jira.domain.JiraProject;
import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import com.deavensoft.timetracker.integration.jira.domain.JiraWorkLog;
import com.deavensoft.timetracker.integration.jira.service.io.JiraExcelExtractor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class JiraExcelExtractorTest {

  @InjectMocks
  JiraExcelExtractor excelImporter;

  File excelFile;
  String date = "4/1/2021";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    String resourceName = "ExcelTest.xlsx";
    ClassLoader classLoader = getClass().getClassLoader();
    excelFile = new File(classLoader.getResource(resourceName).getFile());
  }

  @Test
  void testExtractExcel() throws IOException, ParseException {
    Map<String, List<JiraWorkLog>> result = excelImporter.extractExcel(new FileInputStream(excelFile));
    JiraProject jiraProject = new JiraProject();
    jiraProject.setName("ISYSTEM");
    JiraUser jiraUser = new JiraUser();
    jiraUser.setName("Test Test");
    JiraWorkLog jiraWorkLog = new JiraWorkLog();
    jiraWorkLog.setJiraUser(jiraUser);
    jiraWorkLog.setJiraProject(jiraProject);
    jiraWorkLog.setTopic("Test");
    jiraWorkLog.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy")));
    jiraWorkLog.setHours(6.0);
    jiraWorkLog.setDescription("Test");

    Assertions.assertEquals(new HashMap<String, List<JiraWorkLog>>() {{
      put("Test Test", Arrays.<JiraWorkLog>asList(jiraWorkLog));
    }}, result);
  }
}
