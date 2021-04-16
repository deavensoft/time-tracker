package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.exception.JiraNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface ImporterService {

  void importExcel(InputStream inputStream) throws IOException, JiraNotFoundException;

}
