package com.deavensoft.timetracker.bootstrap;


import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;


    public Bootstrap(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role manager = new Role();
        manager.setRole(Role.UserRole.MANAGER);
        Role employee = new Role();
	    employee.setRole(Role.UserRole.EMPLOYEE);
        Role admin = new Role();
	    admin.setRole(Role.UserRole.ADMIN);
        
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@gmail.com");
		user.setRole(Arrays.asList(manager, employee, admin));

        userRepository.save(user);
    }
}
