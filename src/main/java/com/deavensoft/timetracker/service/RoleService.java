package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.User;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long id);

    List<Role> getAllRoles();

    Role createRole(Role role);

    Role getRoleByUserRole(Role.UserRole userRole);

}
