package com.deavensoft.timetracker.config;

import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import com.deavensoft.timetracker.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public WorkLogService workLogService(WorkLogRepository repository, ProjectRepository projectRepository){
        return new WorkLogServiceImpl(repository, projectRepository);
    }

    @Bean
    public UserService userService(UserRepository repository){
        return new UserServiceImpl(repository);
    }

    @Bean
    public ProjectService projectService(ProjectRepository projectRepository,
                                         UserRepository userRepository) {
        return new ProjectServiceImpl(projectRepository, userRepository);
    }
}
