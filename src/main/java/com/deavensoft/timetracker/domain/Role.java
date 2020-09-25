package com.deavensoft.timetracker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class Role{

    public enum UserRole {
        EMPLOYEE, MANAGER, ADMIN
    }

    @Id
    @Enumerated (EnumType.STRING)
    private UserRole role;
}
