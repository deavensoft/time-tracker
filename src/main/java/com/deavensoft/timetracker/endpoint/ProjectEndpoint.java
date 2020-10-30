package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.ProjectMapper;
import com.deavensoft.timetracker.api.mapper.UserMapper;
import com.deavensoft.timetracker.api.model.ProjectDto;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.service.ProjectService;
import com.deavensoft.timetracker.service.projectService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(ProjectEndpoint.BASE_URL)
@RequiredArgsConstructor
@RestController
public class ProjectEndpoint {
    public static final String BASE_URL = "/v1.0/projects";
    private final ProjectService projectService;
    private final ProjectMapper mapper;

    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable Long id) {
        return mapper.projectToProjectDto(projectService.getProjectById(id));
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects().stream()
		        .map(mapper::projectToProjectDto).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<UserDto> getAllUsersForProject(@RequestParam List<String> role) {
        List<UserDto> usersDto = new ArrayList<>();
        for (String userRole : role) {
            final List<User> admin = projectService.getAllUsersForProject(userRole);

            for (User user : admin) {
                usersDto.add(mapper.userToUserDto(user));
            }
        }

        return usersDto;
    }

    @PostMapping
    public UserDto createProject(@RequestBody UserDto userDto) {
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            throw new IllegalArgumentException("User must have role(s)");
        } else {
            User user = mapper.userDtoToUser(userDto);

            try {
                User returnedUser = projectService.createProject(user);

                return mapper.userToUserDto(returnedUser);
            } catch (DataIntegrityViolationException notUniqEmailError) {
                throw new IllegalArgumentException("Set valid email");
            }
        }
    }

    @PutMapping({"/{id}"})
    public UserDto updateProject(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);

        return mapper.userToUserDto(projectService.updateProject(id, user));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }


}
