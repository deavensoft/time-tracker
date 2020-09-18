package com.deavensoft.timetracker.endpoint;


import com.deavensoft.timetracker.api.mapper.RoleMapper;
import com.deavensoft.timetracker.api.model.RoleDto;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@RequestMapping(RoleEndpoint.BASE_URL)
@RestController
public class RoleEndpoint {
    public static final String BASE_URL = "/v1.0/roles";

    private final RoleService roleService;
    private final RoleMapper mapper;

    @GetMapping("/{id}")
    public RoleDto getRoleById(@PathVariable Long id) {
        return mapper.roleToRoleDto(roleService.getRoleById(id));
    }

    @GetMapping
    public Set<RoleDto> getAllRoles() {
        return roleService.getAllRoles().stream().map(mapper::roleToRoleDto).collect(Collectors.toSet());
    }

    @PostMapping
    public RoleDto createRole(RoleDto roleDto) {
//        return mapper.roleDtoToRole(roleDto);
        return null;
    }


}