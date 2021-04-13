package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
