package com.deavensoft.timetracker.service.io;

import java.io.InputStream;

public interface ExcelExtractor {
   <T> T extractExcel(InputStream inputStream);

}
