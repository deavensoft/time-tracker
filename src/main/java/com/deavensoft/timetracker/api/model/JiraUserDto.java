package com.deavensoft.timetracker.api.model;

import lombok.Data;

@Data
public class JiraUserDto {
  private Long id;
  private String name;
  private Long userId;
  private String userFirstName;
  private String userLastName;
}
