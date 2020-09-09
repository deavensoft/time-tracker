package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.WorkLog;
import org.springframework.data.repository.CrudRepository;

public interface WorkLogRepository extends CrudRepository<WorkLog, Long> {

}
