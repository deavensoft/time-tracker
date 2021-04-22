package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.exception.JiraNotFoundException;
import java.io.InputStream;

public interface JiraImporterService {

  void importExcel(InputStream inputStream) throws JiraNotFoundException;

}
