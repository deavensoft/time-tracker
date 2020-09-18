package com.deavensoft.timetracker.bootstrap;


import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final RoleService roleService;

    public Bootstrap(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

        Role manager = new Role();
        manager.setRole(Role.UserRole.MANAGER);
        roleService.createRole(manager);

        Role employee = new Role();
        employee.setRole(Role.UserRole.EMPLOYEE);
        roleService.createRole(employee);

        Role admin = new Role();
        admin.setRole(Role.UserRole.ADMIN);
        roleService.createRole(admin);
    }
}
