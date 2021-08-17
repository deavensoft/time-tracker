package com.deavensoft.timetracker.repository;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  @Query(
      value = "SELECT  ID, FIRST_NAME,LAST_NAME,IS_ACTIVE, EMAIL\n"
          + "    FROM USER_ROLES\n"
          + "    INNER JOIN USER\n"
          + "    ON USER.ID = USER_ROLES.USER_ID\n"
          + "    WHERE ROLE LIKE :userRole",
      nativeQuery = true)
  List<User> findAllByUserRole(String userRole);

  @Query(value = "SELECT * FROM USER WHERE ID NOT IN (SELECT USER_ID FROM JIRA_USER)", nativeQuery = true)
  List<User> findAllUnmappedUsers();
  @Query("select e from User e where e.firstName = ?1 and e.lastName = ?2")
  User findUserByFirstAndLastName(String firstName, String lastName);
  @Query("select e from User e where e.firstName like ?1 and e.lastName like ?2 ")
  User findUserByFirstNameLikeAndLastNameLike(String firstName, String lastName);




}

