package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.UserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project with ID = " + id + " does not exist!"));
    }

    @Override
    public List<Project> getAllProjects() {
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsersForProject(Long projectId) {
        return projectRepository.findById(projectId)
                .map(Project::getUsers)
                .orElse(Collections.emptyList());
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        getProjectById(id);

        project.setId(id);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isPresent()) {
            Project updateProject = project.get();
            updateProject.setIsActive(false);

            projectRepository.save(updateProject);
        }
    }

    @Override
    public Project addUserOnProject(Long id, Long userId) {
        final Optional<Project> project = projectRepository.findById(id);
        final Optional<User> user = userRepository.findById(userId);

        if (project.isPresent() && user.isPresent()) {
            Project updateProject = project.get();

            if (updateProject.getUsers() == null) {
                updateProject.setUsers(new ArrayList<>());
            }

            updateProject.getUsers().add(user.get());

            return projectRepository.save(updateProject);
        } else {
            throw new IllegalArgumentException("Project or user id not found.");
        }

    }


}
