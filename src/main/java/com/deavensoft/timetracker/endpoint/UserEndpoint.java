package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.UserMapper;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.exception.TimeTrackerException;
import com.deavensoft.timetracker.service.UserService;
import java.util.ArrayList;
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

@RequestMapping(UserEndpoint.BASE_URL)
@RequiredArgsConstructor
@RestController
@CrossOrigin(maxAge = 3600)
public class UserEndpoint {
    public static final String BASE_URL = "/v1.0/users";
    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return mapper.userToUserDto(userService.getUserById(id));
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream().map(mapper::userToUserDto).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<UserDto> getAllUsersByRoles(@RequestParam List<String> role) {
        List<UserDto> usersDto = new ArrayList<>();
        for (String userRole : role) {
            final List<User> admin = userService.getAllUsersByRoles(userRole);

            for (User user : admin) {
                usersDto.add(mapper.userToUserDto(user));
            }
        }

        return usersDto;
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            throw TimeTrackerException.postMessageBodyNotCorrect("User must have role(s)");
        } else {
            User user = mapper.userDtoToUser(userDto);
            User returnedUser = userService.createUser(user);

            return mapper.userToUserDto(returnedUser);
        }
    }

    @PutMapping({"/{id}"})
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);

        return mapper.userToUserDto(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }



}
