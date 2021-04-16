package com.deavensoft.timetracker.service.io;

import com.deavensoft.timetracker.domain.jira.JiraProject;
import com.deavensoft.timetracker.domain.jira.JiraWorkLog;
import com.deavensoft.timetracker.domain.jira.JiraUser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Slf4j
@AllArgsConstructor
public class ExcelImporter implements Importer {

  private enum CellEnum {
    USER, PROJECT, SUMMARY, WORKLOG_STARTED, WORKLOG_CREATED, WORKLOG_HOURS, DESCRIPTION
  }

  @Override
  public Map<String, List<JiraWorkLog>> extractExcel(InputStream inputStream) throws IOException {
    Map<String, List<JiraWorkLog>> workLogMap = new HashMap<>();

    try (InputStream excelFile = inputStream) {
      Workbook workbook = new XSSFWorkbook(excelFile);
      Sheet datatypeSheet = workbook.getSheetAt(1);
      Iterator<Row> iterator = datatypeSheet.iterator();
      iterator.next();     //skip first row
      while (iterator.hasNext()) {
        JiraWorkLog workLog = new JiraWorkLog();
        JiraUser jiraUser = new JiraUser();
        JiraProject jiraProject = new JiraProject();
        Row currentRow = iterator.next();
        Iterator<Cell> cellIterator = currentRow.iterator();
        while (cellIterator.hasNext()) {
          Cell currentCell = cellIterator.next();
          switch (CellEnum.values()[currentCell.getColumnIndex()]) {
            case USER:
              jiraUser.setName(currentCell.getStringCellValue());
              break;
            case PROJECT:
              String projectName = currentCell.getStringCellValue().split("-")[0];
              jiraProject.setName(projectName);
              break;
            case WORKLOG_STARTED:
              LocalDate date = currentCell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
                  .toLocalDate();
              workLog.setDate(date);
              break;
            case WORKLOG_HOURS:
              workLog.setHours(currentCell.getNumericCellValue());
              break;
            case DESCRIPTION:
              workLog.setDescription(currentCell.getStringCellValue());
              break;
          }
        }
        workLog.setJiraProject(jiraProject);
        workLog.setJiraUser(jiraUser);
        if (!workLogMap.containsKey(jiraUser.getName())) {
          workLogMap.put(jiraUser.getName(), new ArrayList<>());
        }
        workLogMap.get(jiraUser.getName()).add(workLog);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return workLogMap;
  }
}