package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.api.model.request.KeycloakAddUserRequest;
import java.util.Arrays;
import java.util.Collections;
import javax.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;


@Component
public class KeycloakServiceImpl implements KeycloakService {

  @Override
  public void addUser(KeycloakAddUserRequest keycloakAddUserRequest,String username, String password) {
    String serverUrl = "http://localhost:8080/auth";
    String realm = "time-tracker-cloak";
    // idm-client needs to allow "Direct Access Grants: Resource Owner Password Credentials Grant"
    String clientId = "time-tracker";

//		// Client "idm-client" needs service-account with at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management"
//		Keycloak keycloak = KeycloakBuilder.builder() //
//				.serverUrl(serverUrl) //
//				.realm(realm) //
//				.grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
//				.clientId(clientId) //
//				.clientSecret(clientSecret).build();

    // User "idm-admin" needs at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management"
    Keycloak keycloak = KeycloakBuilder.builder() //
        .serverUrl(serverUrl) //
        .realm(realm) //
        .grantType(OAuth2Constants.PASSWORD) //
        .password(password)
        .username(username)
        .clientId(clientId) //
        .build();

    // Define user
    UserRepresentation user = new UserRepresentation();
    user.setEnabled(true);
    user.setUsername(String.format("%s %s",keycloakAddUserRequest.getUser().getFirstName(),keycloakAddUserRequest.getUser().getLastName()));
    user.setAttributes(Collections.singletonMap("user_id", Arrays.asList(keycloakAddUserRequest.getUser().getId().toString())));

    // Get realm
    RealmResource realmResource = keycloak.realm(realm);
    UsersResource usersRessource = realmResource.users();

    // Create user (requires manage-users role)
    Response response = usersRessource.create(user);
    System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
    System.out.println(response.getLocation());
    String userId = CreatedResponseUtil.getCreatedId(response);

    System.out.printf("User created with userId: %s%n", userId);

    // Define password credential
    CredentialRepresentation passwordCred = new CredentialRepresentation();
    passwordCred.setTemporary(false);
    passwordCred.setType(CredentialRepresentation.PASSWORD);
    passwordCred.setValue(keycloakAddUserRequest.getPassword());

    UserResource userResource = usersRessource.get(userId);

    // Set password credential
    userResource.resetPassword(passwordCred);

//        // Get realm role "tester" (requires view-realm role)
    RoleRepresentation testerRealmRole = realmResource.roles()//
        .get("user").toRepresentation();
//
//        // Assign realm role tester to user
    userResource.roles().realmLevel() //
        .add(Arrays.asList(testerRealmRole));
//
//        // Get client
    ClientRepresentation app1Client = realmResource.clients() //
        .findByClientId("time-tracker").get(0);
//
//        // Get client level role (requires view-clients role)
    RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
        .roles().get("user").toRepresentation();
//
//        // Assign client level role to user
    userResource.roles() //
        .clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

    // Send password reset E-Mail
    // VERIFY_EMAIL, UPDATE_PROFILE, CONFIGURE_TOTP, UPDATE_PASSWORD, TERMS_AND_CONDITIONS
//        usersRessource.get(userId).executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));

    // Delete User
//        userResource.remove();
  }
}
