package com.deavensoft.timetracker.api.mapper;

import com.deavensoft.timetracker.api.model.UserDto;
import com.deavensoft.timetracker.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
