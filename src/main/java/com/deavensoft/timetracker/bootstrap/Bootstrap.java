package com.deavensoft.timetracker.bootstrap;


import com.deavensoft.timetracker.repository.RoleRepository;
import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.service.RoleService;
import com.deavensoft.timetracker.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public Bootstrap(RoleService roleService, UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.roleService = roleService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//
//        Role manager = new Role();
//        manager.setRole(Role.UserRole.MANAGER);
//        roleService.createRole(manager);
//
//        Role employee = new Role();
//        employee.setRole(Role.UserRole.EMPLOYEE);
//        roleService.createRole(employee);
//
//        Role admin = new Role();
//        admin.setRole(Role.UserRole.ADMIN);
//        roleService.createRole(admin);
//
//        User user = new User();
//        user.setFirstName("firstName");
//        user.setLastName("lastName");
//        user.setEmail("email@gmail.com");
//
//
//        user.addRole(manager);
//        userRepository.save(user);
//
//        User user2 = new User();
//        user2.setFirstName("firstName");
//        user2.setLastName("lastName");
//        user2.setEmail("email222@gmail.com");
//        user2.addRole(manager);
//        user2.addRole(admin);
//        userRepository.save(user2);
    }
}
