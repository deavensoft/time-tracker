package com.deavensoft.timetracker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Role{

    public enum UserRole {
        EMPLOYEE, MANAGER, ADMIN
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated (EnumType.STRING)
    private UserRole role;

    @ManyToMany (mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
