package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.domain.Project;
import com.deavensoft.timetracker.domain.User;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class WorkLogServiceImplTest {

    private Long ID = 1l;
    private LocalDate DATE = LocalDate.now();
    private Double HOURS = 7.5;
    public String TOPIC = "topic";
    public String DESCRIPTION = "description";

    @Mock
    private WorkLogRepository workLogRepository;

    private WorkLog workLog1;
    private WorkLogService workLogService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        workLogService = new WorkLogServiceImpl(workLogRepository);

        workLog1 = new WorkLog();
        workLog1.setId(1L);
        workLog1.setDate(DATE);
        workLog1.setHours(HOURS);
        workLog1.setTopic(TOPIC);
        workLog1.setDescription(DESCRIPTION);
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
        // given
        WorkLog worklog = new WorkLog();
        worklog.setId(5L);
        worklog.setDate(workLog1.getDate());
        worklog.setHours(workLog1.getHours());
        worklog.setTopic(workLog1.getTopic());
        worklog.setDescription("New description");
        Project project = new Project();
        project.setId(1L);
        worklog.setProject(project);
        User user = new User();
        user.setId(2L);
        worklog.setUser(user);



        // when
        WorkLog workLog = workLogService.createWorkLog(worklog);

        // then
        verify(workLogRepository, timeout(1)).save(any());
    }

    @Test
    void createWorkLog_shouldRaiseException_whenUserIsNotInProject() {
        // given
        WorkLog worklog = new WorkLog();
        worklog.setId(5L);
        worklog.setDate(workLog1.getDate());
        worklog.setHours(workLog1.getHours());
        worklog.setTopic(workLog1.getTopic());
        worklog.setDescription("New description");
        Project project = new Project();
        project.setId(1L);
        worklog.setProject(project);
        User user = new User();
        user.setId(2L);
        worklog.setUser(user);



        // when
        WorkLog workLog = workLogService.createWorkLog(worklog);

        // then
        verify(workLogRepository, timeout(1)).save(any());
    }
}
