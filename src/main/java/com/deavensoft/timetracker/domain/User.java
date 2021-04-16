package com.deavensoft.timetracker.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

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
    private List<Role> roles = new ArrayList<>();


    public void addRole(Role newRole) {
        roles.add(newRole);
    }
}
