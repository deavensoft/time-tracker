package com.deavensoft.timetracker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive = true;

    @Column(unique = true)
    private String email;

    @ElementCollection
    @CollectionTable(
		    name="user_roles",
		    joinColumns=@JoinColumn(name="user_id")
    )
    private List<Role> role = new ArrayList<>();


    @OneToMany (mappedBy = "user")
    private List<WorkLog> workLogs = new ArrayList<>();

    public void addRole(Role newRole) {
        role.add(newRole);
    }
}
