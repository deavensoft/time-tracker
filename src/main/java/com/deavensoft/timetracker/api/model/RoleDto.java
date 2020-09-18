package com.deavensoft.timetracker.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDto {

    public enum UserRoleDto {
        EMPLOYEE, MANAGER, ADMIN
    }

    private Long id;
    private UserRoleDto role;
    private List<UserDto> users = new ArrayList<>();
}
