package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;

import java.util.Collection;
import java.util.List;

public interface ProjectService {

	Project getProjectById(Long id);

	List<Project> getAllProjects();

	List<User> getAllUsersForProject(Long projectId);

	Project createProject(Project project);

	Project updateProject(Long id, Project project);

	void deleteProject(Long id);

	Project addUserOnProject(Long id, Long userId);
}