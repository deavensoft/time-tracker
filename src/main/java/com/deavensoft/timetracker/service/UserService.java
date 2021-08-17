package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    List<User> getAllUnmappedUsers();

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    List<User> getAllUsersByRoles(String roles);


}
