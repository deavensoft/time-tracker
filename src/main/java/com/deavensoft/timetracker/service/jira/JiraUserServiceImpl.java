package com.deavensoft.timetracker.service.jira;

import com.deavensoft.timetracker.domain.jira.JiraUser;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.repository.JiraUserRepository;
import com.deavensoft.timetracker.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JiraUserServiceImpl implements JiraUserService {

  private final JiraUserRepository jiraUserRepository;
  private final UserService userService;


  @Override
  public JiraUser crateJiraUser(Long id, JiraUser jiraUser)  {
    User user = null;
    try{
      user = userService.getUserById(id);
    }catch (IllegalArgumentException e){
      user = new User();
      user.setFirstName(jiraUser.getName());
      user.setLastName("");
      user.setIsActive(true);
      user = userService.createUser(user);
    }
    jiraUser.setUser(user);
    return jiraUserRepository.save(jiraUser);
  }

  @Override
  public JiraUser getJiraUserByName(String name) {
    return jiraUserRepository.findByName(name);
  }
}
