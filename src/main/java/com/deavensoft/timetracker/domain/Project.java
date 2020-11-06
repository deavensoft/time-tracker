package com.deavensoft.timetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private Boolean isActive = true;

	@ManyToMany
	@JoinTable(name = "project_users",
			joinColumns = { @JoinColumn(name = "project_id") },
			inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<User> users = new ArrayList<>();
}

