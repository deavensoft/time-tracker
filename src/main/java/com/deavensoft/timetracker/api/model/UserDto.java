package com.deavensoft.timetracker.api.model;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive = true;
    private String email;
}
