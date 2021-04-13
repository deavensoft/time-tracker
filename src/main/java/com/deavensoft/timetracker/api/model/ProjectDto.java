package com.deavensoft.timetracker.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;

    private List<UserDto> users = new ArrayList<>();
}
