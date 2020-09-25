package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.repository.RoleRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role with ID = " + id + " does not exist!"));
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);

        return roles;
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByUserRole(Role.UserRole userRole) {
        return roleRepository.findByRole(userRole);
    }


}
