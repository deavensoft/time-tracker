package com.deavensoft.timetracker.bootstrap;


import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import com.deavensoft.timetracker.service.UserService;
import com.deavensoft.timetracker.service.WorkLogService;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {

  private final UserService userService;
  private final UserRepository userRepository;
  private final WorkLogRepository workLogRepository;
  private final ProjectRepository projectRepository;

  public Bootstrap(UserService userService,
      UserRepository userRepository,
      WorkLogRepository workLogRepository,
      ProjectRepository projectRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.workLogRepository = workLogRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    Role manager = new Role();
    manager.setRole(Role.UserRole.MANAGER);
    Role employee = new Role();
    employee.setRole(Role.UserRole.EMPLOYEE);
    Role admin = new Role();
    admin.setRole(Role.UserRole.ADMIN);

    User user = new User();
    user.setFirstName("firstName");
    user.setLastName("lastName");
    user.setEmail("email@gmail.com");
    user.setRoles(Arrays.asList(manager, employee, admin));

    User user2 = new User();
    user.setFirstName("Aleksandar");
    user.setLastName("Ignjatovic");
    user.setEmail("email@gmail.com");
    user.setRoles(Arrays.asList(manager, employee, admin));
    userRepository.save(user);
    userRepository.save(user2);

    Project project = new Project();
    project.setUsers(Arrays.asList(user));
    project.setIsActive(true);
    project.setName("name");
    project.setDescription("desc");


    WorkLog workLog = new WorkLog();
    workLog.setUser(user);
    workLog.setProject(projectRepository.save(project));
    workLog.setDate(LocalDate.now());
    workLog.setDescription("Worklog desc");
    workLog.setTopic("Topic");
    workLog.setHours(2.2);

    workLogRepository.save(workLog);

  }
}
