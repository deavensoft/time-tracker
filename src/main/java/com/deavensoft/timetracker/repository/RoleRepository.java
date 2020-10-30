package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(Role.UserRole userRole);
}
