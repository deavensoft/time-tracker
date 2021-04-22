package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.Role;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.repository.UserRepository;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class WorkLogServiceImplTest {

  private static final Long ID = 1l;
  private static final LocalDate DATE = LocalDate.now();
  private static final Double HOURS = 7.5;
  private static final String TOPIC = "topic";
  private static final String DESCRIPTION = "description";

  @Mock
  private WorkLogRepository workLogRepository;
  @Mock
  private ProjectRepository projectRepository;
  @Mock
  private UserRepository userRepository;

  private WorkLog workLog1;
  private WorkLog workLog2;
  private WorkLogService workLogService;
  private User user;
  private Project project;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.initMocks(this);

    workLogService = new WorkLogServiceImpl(workLogRepository, projectRepository);

    workLog1 = new WorkLog();
    workLog1.setId(1L);
    workLog1.setDate(DATE);
    workLog1.setHours(HOURS);
    workLog1.setTopic(TOPIC);
    workLog1.setDescription(DESCRIPTION);

    workLog2 = new WorkLog();
    workLog2.setId(2L);
    workLog2.setDate(DATE.minusDays(1));
    workLog2.setHours(HOURS);
    workLog2.setTopic(TOPIC);
    workLog2.setDescription(DESCRIPTION);

    Role employee = new Role();
    employee.setRole(Role.UserRole.EMPLOYEE);

    user = new User();
    user.setId(1L);
    user.setEmail("test@gmail.com");
    user.setFirstName("Test");
    user.setLastName("Test");
    user.setRoles(Arrays.asList(employee));
    user.setIsActive(true);

    project = new Project();
    project.setId(1L);
    project.setUsers(Collections.singletonList(user));

    workLog1.setProject(project);
    workLog1.setUser(user);
    workLog2.setProject(project);
    workLog2.setUser(user);

    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

  }

  @Test
  void getWorkLogById() {
    //given
    WorkLog workLog = new WorkLog();
    workLog.setId(ID);
    workLog.setDate(DATE);
    workLog.setHours(HOURS);
    workLog.setTopic(TOPIC);
    workLog.setDescription(DESCRIPTION);

    //when
    when(workLogRepository.findById(anyLong())).thenReturn(Optional.of(workLog));

    WorkLog returnedWorkLogDto = workLogService.getWorkLogById(ID);

    //then
    assertEquals(ID, returnedWorkLogDto.getId());

  }

  @Test
  void getAllWorkLogs() {
    //given
    WorkLog workLog1 = new WorkLog();
    workLog1.setId(1L);
    workLog1.setDate(DATE);
    workLog1.setHours(HOURS);
    workLog1.setTopic(TOPIC);
    workLog1.setDescription(DESCRIPTION);

    WorkLog workLog2 = new WorkLog();
    workLog2.setId(2L);
    workLog2.setDate(DATE);
    workLog2.setHours(HOURS);
    workLog2.setTopic(TOPIC);
    workLog2.setDescription(DESCRIPTION);

    //when
    when(workLogRepository.findAll()).thenReturn(Arrays.asList(workLog1, workLog2));

    List<WorkLog> workLogsDto = workLogService.getAllWorkLogs();

    //then
    assertEquals(2, workLogsDto.size());

  }

  @Test
  void createWorkLog() {
    //given
    when(workLogRepository.save(any(WorkLog.class))).thenReturn(workLog1);

    //when
    WorkLog savedWorkLog = workLogService.createWorkLog(workLog1);

    //then
    assertEquals(workLog1.getId(), savedWorkLog.getId());
    assertEquals(workLog1.getDate(), savedWorkLog.getDate());
    assertEquals(workLog1.getHours(), savedWorkLog.getHours());
    assertEquals(workLog1.getTopic(), savedWorkLog.getTopic());
    assertEquals(workLog1.getDescription(), savedWorkLog.getDescription());
  }

  @Test
  void updateWorkLog() {
    //given
    WorkLog updateWorkLog = new WorkLog();
    updateWorkLog.setId(5L);
    updateWorkLog.setDate(workLog1.getDate());
    updateWorkLog.setHours(workLog1.getHours());
    updateWorkLog.setTopic(workLog1.getTopic());
    updateWorkLog.setDescription("New description");
    updateWorkLog.setUser(user);
    updateWorkLog.setProject(project);

    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
    when(workLogRepository.save(any(WorkLog.class))).thenReturn(workLog1);

    //when
    WorkLog savedDto = workLogService.updateWorkLog(ID, updateWorkLog);

    //then
    assertEquals(updateWorkLog.getId(), savedDto.getId());
    assertEquals(updateWorkLog.getDate(), savedDto.getDate());
    assertEquals(updateWorkLog.getHours(), savedDto.getHours());
    assertEquals(updateWorkLog.getTopic(), savedDto.getTopic());
    assertNotEquals(updateWorkLog.getDescription(), savedDto.getDescription());
  }

  @Test
  void deleteWorkLog() {
    workLogRepository.deleteById(ID);

    verify(workLogRepository, times(1)).deleteById(anyLong());
  }


  @Test
  void createWorkLog_shouldSaveWorklog_whenUserIsInProject() {
    // when
    WorkLog workLog = workLogService.createWorkLog(workLog1);

    // then
    verify(workLogRepository, timeout(1)).save(any());
  }

  @Test
  void createWorkLog_shouldRaiseException_whenUserIsNotInProject() {
    //given
    project.setUsers(null);

    // then
    assertThrows(IllegalArgumentException.class, () -> {
      workLogService.createWorkLog(workLog1);
    });
  }

  @Test
  void getWorkLogsBetweenDates() {
    when(workLogRepository
        .findWorkLogsBetweenDates(any(), any()))
        .thenReturn(Arrays.asList(workLog1, workLog2));

    List<WorkLog> workLogs = workLogService
        .getWorkLogsBetweenDates(LocalDate.now().minusDays(2), LocalDate.now().plusDays(2));

    assertArrayEquals(Arrays.asList(workLog1, workLog2).toArray(), workLogs.toArray());
  }

  @Test
  void getWorkLogsBetweenDatesWithUser() {
    when(workLogRepository
        .findWorkLogsBetweenDatesWithUser(anyLong(), any(), any()))
        .thenReturn(Arrays.asList(workLog1, workLog2));

    List<WorkLog> workLogs = workLogService
        .getWorkLogsBetweenDatesWithUser(workLog1.getUser().getId(), LocalDate.now().minusDays(2),
            LocalDate.now().plusDays(2));

    assertArrayEquals(Arrays.asList(workLog1, workLog2).toArray(), workLogs.toArray());
  }

  @Test
  void getWorkLogsBetweenDatesWithProject() {
    when(workLogRepository
        .findWorkLogsBetweenDatesWithProject(anyLong(), any(), any()))
        .thenReturn(Arrays.asList(workLog1, workLog2));

    List<WorkLog> workLogs = workLogService
        .getWorkLogsBetweenDatesWithProject(workLog1.getProject().getId(),
            LocalDate.now().minusDays(2), LocalDate.now().plusDays(2));

    assertArrayEquals(Arrays.asList(workLog1, workLog2).toArray(), workLogs.toArray());
  }

  @Test
  void getWorkLogsBetweenDatesWithProjectAndUsers() {
    when(workLogRepository
        .findWorkLogsBetweenDatesWithProjectAndUser(anyLong(), anyLong(), any(), any()))
        .thenReturn(Arrays.asList(workLog1, workLog2));

    List<WorkLog> workLogs = workLogService
        .getWorkLogsBetweenDatesWithProjectAndUsers(workLog1.getProject().getId(),
            workLog1.getUser().getId(), LocalDate.now().minusDays(2), LocalDate.now().plusDays(2));

    assertArrayEquals(Arrays.asList(workLog1, workLog2).toArray(), workLogs.toArray());
  }
}
