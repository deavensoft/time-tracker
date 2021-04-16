package com.deavensoft.timetracker.api.model.request;

import java.util.Map;
import lombok.Data;

@Data
public class JiraMappingRequest {
    Map<String,Long> users;
    Map<String,Long> projects;
}
