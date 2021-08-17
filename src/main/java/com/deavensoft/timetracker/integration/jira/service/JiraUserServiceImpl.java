package com.deavensoft.timetracker.integration.jira.service;

import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.repository.JiraUserRepository;
import com.deavensoft.timetracker.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JiraUserServiceImpl implements JiraUserService {

  private final JiraUserRepository jiraUserRepository;
  private final UserService userService;

  @Override
  public List<JiraUser> getAllJiraUsers() {
    return StreamSupport.stream(jiraUserRepository.findAll().spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public JiraUser crateJiraUser(Long id, JiraUser jiraUser)  {
    User user = null;
    try{
      user = userService.getUserById(id);
    }catch (IllegalArgumentException e){
      user = createUserWithJiraUserName(jiraUser);
    }
    jiraUser.setUser(user);
    return jiraUserRepository.save(jiraUser);
  }

  @Override
  public JiraUser updateJiraUser(Long id, JiraUser user) {
    user.setId(id);

    return jiraUserRepository.save(user);
  }

  @Override
  public void deleteJiraUser(Long id) {
    jiraUserRepository.deleteById(id);
  }

  private User createUserWithJiraUserName(JiraUser jiraUser){
    User user = new User();
    user.setFirstName(jiraUser.getName());
    user.setLastName("");
    user.setIsActive(true);
    return userService.createUser(user);
  }

  @Override
  public JiraUser getJiraUserByName(String name) {
    return jiraUserRepository.findByName(name);
  }

  @Override
  public JiraUser mapUserToJiraUser(JiraUser jiraUser) {
   return jiraUserRepository.save(jiraUser);
  }
}
