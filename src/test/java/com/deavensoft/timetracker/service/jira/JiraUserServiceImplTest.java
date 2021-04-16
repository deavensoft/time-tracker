package com.deavensoft.timetracker.service.jira;

import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.Role.UserRole;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.jira.JiraUser;
import com.deavensoft.timetracker.repository.JiraUserRepository;
import com.deavensoft.timetracker.service.UserService;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JiraUserServiceImplTest {

  @Mock
  JiraUserRepository jiraUserRepository;
  @Mock
  UserService userService;
  @InjectMocks
  JiraUserServiceImpl jiraUserServiceImpl;

  User user;
  @BeforeEach
  void setUp() {

    MockitoAnnotations.initMocks(this);
    Role role = new Role();
    role.setRole(UserRole.EMPLOYEE);

    user = new User();
    user.setId(1L);
    user.setFirstName("TestMe");
    user.setLastName("");
    user.setRoles(Arrays.asList(role));
  }

  @Test
  void testCrateJiraUser() {
    JiraUser jiraUser = new JiraUser();
    jiraUser.setName("Test");
    jiraUser.setUser(user);
    when(userService.getUserById(1L)).thenReturn(user);
    when(userService.createUser(user)).thenReturn(user);
    when(jiraUserRepository.save(jiraUser)).thenReturn(jiraUser);

    JiraUser result = jiraUserServiceImpl.crateJiraUser(1L,jiraUser);
    assertEquals(jiraUser, result);
    assertEquals(user, result.getUser());
  }

  @Test
  void testCrateJiraUser_createUserWhenNotFound() {
    JiraUser jiraUser = new JiraUser();
    jiraUser.setName("Test");
    user.setFirstName(jiraUser.getName());
    jiraUser.setUser(user);
//    when(userService.getUserById(1L)).thenReturn(user);
    when(userService.getUserById(1L)).thenReturn(user);
    when(userService.getUserById(2L)).thenThrow(IllegalArgumentException.class);
    when(userService.createUser(any())).thenReturn(user);
    when(jiraUserRepository.save(jiraUser)).thenReturn(jiraUser);

    JiraUser result = jiraUserServiceImpl.crateJiraUser(2L,jiraUser);
    assertEquals(jiraUser.getUser().getFirstName(), result.getUser().getFirstName());
    assertEquals(user, result.getUser());
  }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme