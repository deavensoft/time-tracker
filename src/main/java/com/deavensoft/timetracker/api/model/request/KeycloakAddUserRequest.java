package com.deavensoft.timetracker.api.model.request;

import com.deavensoft.timetracker.api.model.UserDto;
import lombok.Data;

@Data
public class KeycloakAddUserRequest {
  String password;
  UserDto user;
}
