package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.UserMapper;
import com.deavensoft.timetracker.api.model.RoleDto;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserEndpointTest {

    public static final String BASE_URL = "/v1.0/users";

    @Mock
    private UserService userService;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserEndpoint userEndpoint;

    private MockMvc mockMvc;
    private User user;
    private UserDto userDto;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userEndpoint).build();

        Role manager = new Role();
        manager.setRole(Role.UserRole.EMPLOYEE);

        RoleDto managerDto = new RoleDto();
        managerDto.setRole(RoleDto.UserRoleDto.EMPLOYEE);
        List rolesDto = new ArrayList();
        rolesDto.add(managerDto);

        user = new User();
        user.setId(1L);
        user.setFirstName("Franklin");
        user.setLastName("George");
        user.setEmail("frank.george@xyz.com");
        user.setIsActive(true);
        user.addRole(manager);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Franklin");
        userDto.setLastName("George");
        userDto.setEmail("frankty.george@xyz.com");
        userDto.setIsActive(true);
        userDto.setRoles(rolesDto);
    }

    @Test
    void getUserTest() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(mapper.userToUserDto(any())).thenReturn(userDto);

        mockMvc.perform(get(BASE_URL + "/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo(userDto.getEmail())));
    }

    @Test
    void getAllUsersTest() throws Exception {
        User user1 = new User();
        user1.setFirstName("Alice");
        user1.setLastName("Price");
        user1.setEmail("alice.p@xyz.com");

        User user2 = new User();
        user2.setFirstName("Shawn");
        user2.setLastName("Contreras");
        user2.setEmail("s.contreras@xyz.com");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
        when(mapper.userToUserDto(any())).thenReturn(new UserDto());

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void createUserTest() throws Exception {
        when(userService.createUser(any())).thenReturn(user);
        when(mapper.userToUserDto(any())).thenReturn(userDto);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.firstName", equalTo(userDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(userDto.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(userDto.getEmail())))
                .andExpect(jsonPath("$.roles[0].role", equalTo(userDto.getRoles().get(0).getRole().name())));
    }




}
