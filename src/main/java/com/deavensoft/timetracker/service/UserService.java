package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.User;

import com.deavensoft.timetracker.domain.jira.JiraUser;
import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    List<User> getAllUsersByRoles(String roles);


}
