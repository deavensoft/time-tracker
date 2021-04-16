package com.deavensoft.timetracker.service.io;

import com.deavensoft.timetracker.domain.jira.JiraWorkLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface Importer {
  Map<String, List<JiraWorkLog>> extractExcel(InputStream inputStream) throws IOException;

}
