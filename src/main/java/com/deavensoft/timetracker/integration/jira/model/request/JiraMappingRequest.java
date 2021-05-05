package com.deavensoft.timetracker.integration.jira.model.request;

import java.util.Map;
import lombok.Data;

@Data
public class JiraMappingRequest {
    Map<String,Long> users;
    Map<String,Long> projects;
}
