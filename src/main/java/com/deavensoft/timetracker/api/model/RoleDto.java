package com.deavensoft.timetracker.api.model;

import lombok.Data;

@Data
public class RoleDto {
    public enum UserRoleDto {
        EMPLOYEE, MANAGER, ADMIN
    }

    private UserRoleDto role;
}
