package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.repository.ProjectRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {

    private static final String TEST_PROJECT_NAME = "Test Project";
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    private Project testProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

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

        // given
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            projectService.getProjectById(1L);
        });
    }

    @Test
    void getAllProjects_shouldReturnAllProjects() {

	    // given
	    when(projectRepository.findAll()).thenReturn(Arrays.asList(
	    		Project.builder().id(1L).name("Test Project 1").build(),
			    Project.builder().id(2L).name("Test Project 2").build(),
			    Project.builder().id(3L).name("Test Project 3").build()));

	    // when
	    List<Project> projects = projectService.getAllProjects();

	    // then
	    MatcherAssert.assertThat(projects, Matchers.is(Matchers.notNullValue()));
	    MatcherAssert.assertThat(projects, Matchers.hasSize(3));
	    MatcherAssert.assertThat(projects.get(0).getId(), Matchers.is(1L));

    }

    @Test
    void getAllUsersForProject() {

    }

    @Test
    void createProject_shouldReturnIdAndName() {
        when(projectRepository.save(any())).thenReturn(testProject);

        Project project2 = projectService.createProject(testProject);

        assertEquals(testProject.getId(), project2.getId());
        assertEquals(testProject.getName(), project2.getName());
    }

    @Test
    void updateProject_shouldThrowException_whenWrongId() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            projectService.updateProject(2L, testProject);
        });

    }

    @Test
    void deleteProject() {
    }
}
