package com.deavensoft.timetracker.config;

import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import com.deavensoft.timetracker.service.UserService;
import com.deavensoft.timetracker.service.UserServiceImpl;
import com.deavensoft.timetracker.service.WorkLogService;
import com.deavensoft.timetracker.service.WorkLogServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public WorkLogService workLogService(WorkLogRepository repository){
        return new WorkLogServiceImpl(repository);
    }

    @Bean
    public UserService userService(UserRepository repository){
        return new UserServiceImpl(repository);
    }
}
