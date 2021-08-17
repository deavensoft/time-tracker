package com.deavensoft.timetracker.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private Boolean isActive;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "project_users",
			joinColumns = { @JoinColumn(name = "project_id") },
			inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<User> users = new ArrayList<>();

}

