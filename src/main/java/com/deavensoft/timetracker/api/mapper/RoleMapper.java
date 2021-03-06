package com.deavensoft.timetracker.api.mapper;


import com.deavensoft.timetracker.api.model.RoleDto;
import com.deavensoft.timetracker.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto roleToRoleDto(Role role);

    Role roleDtoToRole(RoleDto roleDto);

    RoleDto.UserRoleDto userRoleToUserRoleDto(Role.UserRole userRole);

    Role.UserRole userRoleDtoToUserRole(RoleDto.UserRoleDto userRoleDto);
}
