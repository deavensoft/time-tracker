package com.deavensoft.timetracker.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Embeddable
public class Role{

    public enum UserRole {
        EMPLOYEE, MANAGER, ADMIN
    }
    
    @Enumerated (EnumType.STRING)
    private UserRole role;
}
