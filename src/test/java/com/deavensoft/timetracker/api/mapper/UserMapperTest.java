package com.deavensoft.timetracker.api.mapper;

import com.deavensoft.timetracker.api.model.RoleDto;
import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {

    private Long id = 1L;
    private String firstName = "Alice";
    private String lastName = "Vickers";
    private Boolean isActive = false;
    private String email = "alice.vickers@xyz.com";
    private List<Role> roles = new ArrayList<>();

    private List<RoleDto> rolesDto = new ArrayList<>();

    UserMapper mapper = UserMapper.INSTANCE;

    @Test
    void userToUserDto(){
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsActive(isActive);
        user.setEmail(email);
        user.setRoles(roles);

        UserDto userDto = mapper.userToUserDto(user);

        //then
        assertNotNull(userDto);
        assertEquals(id, userDto.getId());
        assertEquals(firstName, userDto.getFirstName());
        assertEquals(lastName, userDto.getLastName());
        assertEquals(email, userDto.getEmail());
        assertEquals(isActive, userDto.getIsActive());
        assertNotNull(userDto.getRoles());
    }

    @Test
    void userDtoToUser(){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setIsActive(isActive);
        userDto.setEmail(email);
        userDto.setRoles(rolesDto);

        User user = mapper.userDtoToUser(userDto);

        //then
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(isActive, user.getIsActive());
        assertNotNull(user.getRoles());
    }
}
