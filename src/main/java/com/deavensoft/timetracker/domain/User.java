package com.deavensoft.timetracker.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive = true;

    @Column(unique = true)
    private String email;
}
