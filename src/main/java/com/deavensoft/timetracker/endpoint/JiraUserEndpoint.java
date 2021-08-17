package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.JiraUserMapper;
import com.deavensoft.timetracker.api.model.JiraUserDto;
import com.deavensoft.timetracker.integration.jira.domain.JiraUser;
import com.deavensoft.timetracker.integration.jira.service.JiraUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(JiraUserEndpoint.BASE_URL)
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Slf4j
@RequiredArgsConstructor
public class JiraUserEndpoint {
  public static final String BASE_URL = "/v1.0/jira-user";

  private final JiraUserService jiraUserService;
  private final JiraUserMapper mapper;

  @GetMapping
  public List<JiraUserDto> getAllJiraUsers(){
   return jiraUserService.getAllJiraUsers().stream().map(mapper::jiraUserToJiraUserDto).collect(Collectors.toList());

  }
  @PostMapping("/map-user")
  public JiraUserDto mapUser(@RequestBody JiraUserDto jiraUserDto){
    JiraUser user = jiraUserService.mapUserToJiraUser(mapper.jiraUserDtoToJiraUser(jiraUserDto));
    return mapper.jiraUserToJiraUserDto(user);
  }
  @PutMapping("/update-user/{id}")
  public JiraUserDto updateJiraUser(@PathVariable Long id, @RequestBody JiraUserDto jiraUserDto){
   JiraUser user = jiraUserService.updateJiraUser(id,mapper.jiraUserDtoToJiraUser(jiraUserDto));

   return  mapper.jiraUserToJiraUserDto(user);
  }
  @DeleteMapping("/delete-user/{id}")
  public void deleteJiraUser(@PathVariable Long id){
    jiraUserService.deleteJiraUser(id);
  }

}
