package com.deavensoft.timetracker.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Role {

  @Enumerated(EnumType.STRING)
  private UserRole role;

  public enum UserRole {
    EMPLOYEE, MANAGER, ADMIN
  }
}
