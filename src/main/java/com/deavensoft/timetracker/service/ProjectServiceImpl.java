package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

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
}
