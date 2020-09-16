package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
