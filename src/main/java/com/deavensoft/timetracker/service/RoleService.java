package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Role;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long id);

    List<Role> getAllRoles();

    Role createRole(Role role);
}
