package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.repository.ProjectRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {

    private static final String TEST_PROJECT_NAME = "Test Project";
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    private Project testProject;

    @BeforeEach
    void setUp() {
        projectService = new ProjectServiceImpl(projectRepository);

        testProject = new Project();
        testProject.setId(5L);
        testProject.setName(TEST_PROJECT_NAME);
    }

    @Test
    void getProjectById_shouldReturnObject() {
        // given
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(testProject));

        // when
        Project project = projectService.getProjectById(5L);

        // then
        assertEquals(TEST_PROJECT_NAME, project.getName());
    }

    @Test
    void getProjectById_shouldRaiseException_WhenNotFound() {
    }

    @Test
    void getAllProjects() {
    }

    @Test
    void getAllUsersForProject() {
    }

    @Test
    void createProject() {
    }

    @Test
    void updateProject() {
    }

    @Test
    void deleteProject() {
    }
}
