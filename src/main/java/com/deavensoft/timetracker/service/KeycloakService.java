package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.api.model.request.KeycloakAddUserRequest;

public interface KeycloakService {

  void addUser(KeycloakAddUserRequest keycloakAddUserRequest,String username, String password);

}
