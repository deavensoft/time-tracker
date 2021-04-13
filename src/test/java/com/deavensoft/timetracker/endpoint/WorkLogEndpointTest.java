package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.WorkLogMapper;
import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.ProjectRepository;
import com.deavensoft.timetracker.service.WorkLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkLogEndpointTest {

  public static final String BASE_URL = "/v1.0/worklogs";

  @Mock
  private WorkLogService workLogService;
  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private WorkLogMapper mapper;

  @InjectMocks
  private WorkLogEndpoint workLogEndpoint;

  private MockMvc mockMvc;
  private WorkLogDto workLogDto;
  private WorkLog returnedWorkLog;
  private Project project;

  private final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    User user = new User();
    mockMvc = MockMvcBuilders.standaloneSetup(workLogEndpoint).build();
    user.setId(1L);
    project = new Project();
    project.setId(1L);
    project.setUsers(Arrays.asList(user));
    project.setName("Test");
    project.setIsActive(false);

    workLogDto = new WorkLogDto();
    workLogDto.setId(1L);
    workLogDto.setDate(LocalDate.parse("2020-09-09"));
    workLogDto.setHours(5.5);
    workLogDto.setTopic("topic 1");
    workLogDto.setDescription("description 1");
    workLogDto.setUserId(1L);
    workLogDto.setProjectId(project.getId());

    returnedWorkLog = new WorkLog();
    returnedWorkLog.setId(1L);
    returnedWorkLog.setDate(LocalDate.parse("2020-09-09"));
    returnedWorkLog.setHours(5.5);
    returnedWorkLog.setTopic("topic 1");
    returnedWorkLog.setDescription("description 1");
    returnedWorkLog.setUser(user);
    returnedWorkLog.setProject(project);

  }

  @Test
  void getWorkLogTest() throws Exception {
    when(workLogService.getWorkLogById(anyLong())).thenReturn(returnedWorkLog);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    mockMvc.perform(get(BASE_URL + "/" + workLogDto.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description", equalTo(workLogDto.getDescription())));
  }

  @Test
  void getWorkLogsTest() throws Exception {
    WorkLog workLog1 = new WorkLog();
    workLog1.setId(1L);
    workLog1.setDate(LocalDate.now());
    workLog1.setHours(5.5);
    workLog1.setTopic("topic 1");
    workLog1.setDescription("description 1");

    WorkLog workLog2 = new WorkLog();
    workLog2.setId(2L);
    workLog2.setDate(LocalDate.now());
    workLog2.setHours(3.5);
    workLog2.setTopic("topic 2");
    workLog2.setDescription("description 2");

    when(workLogService.getAllWorkLogs()).thenReturn(Arrays.asList(workLog1, workLog2));
    when(mapper.workLogToWorkLogDto(any())).thenReturn(new WorkLogDto());

    mockMvc.perform(get(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void createWorkLogTest() throws Exception {
    when(workLogService.createWorkLog(any())).thenReturn(returnedWorkLog);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    mockMvc.perform(post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(workLogDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(workLogDto.getId()))
        .andExpect(jsonPath("$.hours", equalTo(workLogDto.getHours())))
        .andExpect(jsonPath("$.topic", equalTo(workLogDto.getTopic())))
        .andExpect(jsonPath("$.description", equalTo(workLogDto.getDescription())))
        .andExpect(jsonPath("$.project_id", equalTo(workLogDto.getProjectId().intValue())));
  }

  @Test
  void createWorkLogTest_raiseException_whenUserIdIsNull() {
    workLogDto.setUserId(null);
    when(workLogService.createWorkLog(any())).thenReturn(returnedWorkLog);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    NestedServletException nestedServletException = assertThrows(NestedServletException.class,
        () -> {
          mockMvc.perform(post(BASE_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(workLogDto)));
        });
    assertTrue(nestedServletException.getCause() instanceof IllegalArgumentException);
    assertEquals("User id must not be null!", nestedServletException.getCause().getMessage());
  }

  @Test
  void createWorkLogTest_raiseException_whenProjectIdIsNull() {
    workLogDto.setProjectId(null);
    when(workLogService.createWorkLog(any())).thenReturn(returnedWorkLog);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    NestedServletException nestedServletException = assertThrows(NestedServletException.class,
        () -> {
          mockMvc.perform(post(BASE_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(workLogDto)));
        });
    assertTrue(nestedServletException.getCause() instanceof IllegalArgumentException);
    assertEquals("Project id must not be null!", nestedServletException.getCause().getMessage());
  }

  @Test
  void updateWorkLogTest() throws Exception {
    when(workLogService.updateWorkLog(anyLong(), any())).thenReturn(returnedWorkLog);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    mockMvc.perform(put(BASE_URL + "/" + returnedWorkLog.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(workLogDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(workLogDto.getId()))
        .andExpect(jsonPath("$.hours", equalTo(workLogDto.getHours())))
        .andExpect(jsonPath("$.topic", equalTo(workLogDto.getTopic())))
        .andExpect(jsonPath("$.description", equalTo(workLogDto.getDescription())))
        .andExpect(jsonPath("$.project_id", equalTo(workLogDto.getProjectId().intValue())));

  }

  @Test
  void deleteWorkLogById() {
    workLogService.deleteWorkLog(workLogDto.getId());

    verify(workLogService, times(1)).deleteWorkLog(anyLong());
  }

  @Test
  void getWorkLogsBetweenDates() throws Exception {
    List<WorkLog> workLogList = Arrays.asList(returnedWorkLog);
    when(workLogService.getWorkLogsBetweenDates(any(), any())).thenReturn(workLogList);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    mockMvc.perform(get(BASE_URL + "/" + LocalDate.now() + "/" + LocalDate.now())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(workLogDto.getId()))
        .andExpect(jsonPath("$[0].hours", equalTo(workLogDto.getHours())))
        .andExpect(jsonPath("$[0].topic", equalTo(workLogDto.getTopic())))
        .andExpect(jsonPath("$[0].description", equalTo(workLogDto.getDescription())))
        .andExpect(jsonPath("$[0].project_id", equalTo(workLogDto.getProjectId().intValue())));
  }

  @Test
  void getWorkLogsBetweenDatesWithUser() throws Exception {
    List<WorkLog> workLogList = Arrays.asList(returnedWorkLog);
    when(workLogService.getWorkLogsBetweenDatesWithUser(anyLong(), any(), any()))
        .thenReturn(workLogList);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    mockMvc.perform(
        get(BASE_URL + "/user/" + returnedWorkLog.getUser().getId() + "/" + LocalDate.now() + "/"
            + LocalDate.now())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(workLogDto.getId()))
        .andExpect(jsonPath("$[0].hours", equalTo(workLogDto.getHours())))
        .andExpect(jsonPath("$[0].topic", equalTo(workLogDto.getTopic())))
        .andExpect(jsonPath("$[0].description", equalTo(workLogDto.getDescription())))
        .andExpect(jsonPath("$[0].project_id", equalTo(workLogDto.getProjectId().intValue())));
  }

  @Test
  void getWorkLogsBetweenDatesWithProject() throws Exception {
    List<WorkLog> workLogList = Arrays.asList(returnedWorkLog);
    when(workLogService.getWorkLogsBetweenDatesWithProject(anyLong(), any(), any()))
        .thenReturn(workLogList);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    mockMvc.perform(
        get(BASE_URL + "/project/" + returnedWorkLog.getProject().getId() + "/" + LocalDate.now()
            + "/"
            + LocalDate.now())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(workLogDto.getId()))
        .andExpect(jsonPath("$[0].hours", equalTo(workLogDto.getHours())))
        .andExpect(jsonPath("$[0].topic", equalTo(workLogDto.getTopic())))
        .andExpect(jsonPath("$[0].description", equalTo(workLogDto.getDescription())))
        .andExpect(jsonPath("$[0].project_id", equalTo(workLogDto.getProjectId().intValue())));
  }

  @Test
  void getWorkLogsBetweenDatesWithProjectAndUser() throws Exception {
    List<WorkLog> workLogList = Arrays.asList(returnedWorkLog);
    when(workLogService
        .getWorkLogsBetweenDatesWithProjectAndUsers(anyLong(), anyLong(), any(), any()))
        .thenReturn(workLogList);
    when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

    mockMvc.perform(
        get(BASE_URL + "/" + returnedWorkLog.getProject().getId() + "/" + returnedWorkLog.getUser()
            .getId() + "/" + LocalDate.now() + "/"
            + LocalDate.now())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(workLogDto.getId()))
        .andExpect(jsonPath("$[0].hours", equalTo(workLogDto.getHours())))
        .andExpect(jsonPath("$[0].topic", equalTo(workLogDto.getTopic())))
        .andExpect(jsonPath("$[0].description", equalTo(workLogDto.getDescription())))
        .andExpect(jsonPath("$[0].project_id", equalTo(workLogDto.getProjectId().intValue())));
  }

}
