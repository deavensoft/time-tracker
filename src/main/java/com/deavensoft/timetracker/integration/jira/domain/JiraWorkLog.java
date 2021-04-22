package com.deavensoft.timetracker.integration.jira.domain;

import com.deavensoft.timetracker.domain.WorkLog;
import java.time.LocalDate;
import lombok.Data;

@Data
public class JiraWorkLog {

  private JiraUser jiraUser;
  private JiraProject jiraProject;

  public JiraWorkLog() {
    jiraProject = new JiraProject();
    jiraUser = new JiraUser();
  }

  private LocalDate date;
  private Double hours;
  private String topic;
  private String description;

  public WorkLog toWorkLog(){
    WorkLog workLog = new WorkLog();
    workLog.setDate(date);
    workLog.setHours(hours);
    workLog.setTopic(topic);
    workLog.setDescription(description);
    workLog.setProject(jiraProject.getProject());
    workLog.setUser(jiraUser.getUser());
    return workLog;
  }

}
