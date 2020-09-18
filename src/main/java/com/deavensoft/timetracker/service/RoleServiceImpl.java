package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
}
