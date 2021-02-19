package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  @Query(
      value = "SELECT  ID, FIRST_NAME,LAST_NAME,IS_ACTIVE, EMAIL, ROLE_ID\n" +
          "FROM USER_ROLES \n" +
          "INNER JOIN USER\n" +
          "ON USER.ID = USER_ROLES.USER_ID\n" +
          "WHERE ROLE_ID LIKE :userRole",
      nativeQuery = true)
  List<User> findAllByUserRole(String userRole);
}
