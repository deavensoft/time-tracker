package com.deavensoft.timetracker.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive = true;
    private String email;
    private List<RoleDto> roles = new ArrayList<>();
}
