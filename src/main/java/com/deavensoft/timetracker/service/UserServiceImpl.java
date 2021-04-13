package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with ID = " + id + " does not exist!"));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        user.setId(id);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User updateUser = user.get();
            updateUser.setIsActive(false);

            userRepository.save(updateUser);
        }
    }

    @Override
    public List<User> getAllUsersByRoles(String role) {
        return userRepository.findAllByUserRole(role);
    }

}
