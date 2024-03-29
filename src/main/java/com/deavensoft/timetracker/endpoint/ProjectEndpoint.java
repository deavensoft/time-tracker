package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.ProjectMapper;
import com.deavensoft.timetracker.api.mapper.UserMapper;
import com.deavensoft.timetracker.api.model.ProjectDto;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.service.ProjectService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ProjectEndpoint.BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ProjectEndpoint {
    public static final String BASE_URL = "/v1.0/projects";
    private final ProjectService projectService;
    private final ProjectMapper mapper;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable Long id) {
        return mapper.projectToProjectDto(projectService.getProjectById(id));
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        System.out.println("Testing: "+ System.getenv("PORT"));
        System.out.println("Testing 2: "+ System.getProperty("PORT"));

        return projectService.getAllProjects().stream()
		        .map(mapper::projectToProjectDto).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<ProjectDto> getProjectByUser(@PathVariable Long userId) {
        return projectService.getProjectsWithUserById(userId).stream()
            .map(mapper::projectToProjectDto).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<UserDto> getAllUsersForProject(@RequestParam Long projectId) {
        return projectService.getAllUsersForProject(projectId).stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        Project project = projectService.createProject(mapper.projectDtoToProject(projectDto));

        return mapper.projectToProjectDto(project);
    }

    @PutMapping("/{id}/add-user/{userId}")
    public ProjectDto addUser(@PathVariable Long id, @PathVariable Long userId) {

        Project project = projectService.addUserOnProject(id, userId);

        return mapper.projectToProjectDto(project);
    }


    @PutMapping({"/{id}"})
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        Project project = mapper.projectDtoToProject(projectDto);

        return mapper.projectToProjectDto(projectService.updateProject(id, project));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

}
