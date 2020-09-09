package com.deavensoft.timetracker.service;

import com.deavensoft.timetracker.api.mapper.WorkLogMapper;
import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.repository.WorkLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    WorkLogMapper mapper = WorkLogMapper.INSTANCE;

    private WorkLog workLog1;
    private WorkLogService workLogService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        workLogService = new WorkLogServiceImpl(workLogRepository, mapper);

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

        WorkLogDto returnedWorkLogDto = workLogService.getWorkLogById(ID);

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

        List<WorkLogDto> workLogsDto = workLogService.getAllWorkLogs();

        //then
        assertEquals(2, workLogsDto.size());

    }

    @Test
    void createWorkLog() {
        //given
        WorkLogDto workLogDto = new WorkLogDto();
        workLogDto.setId(workLog1.getId());
        workLogDto.setDate(workLog1.getDate());
        workLogDto.setHours(workLog1.getHours());
        workLogDto.setTopic(workLog1.getTopic());
        workLogDto.setDescription(workLog1.getDescription());

        when(workLogRepository.save(any(WorkLog.class))).thenReturn(workLog1);

        //when
        WorkLogDto savedDto = workLogService.createWorkLog(workLogDto);

        //then
        assertEquals(workLogDto.getId(), savedDto.getId());
        assertEquals(workLogDto.getDate(), savedDto.getDate());
        assertEquals(workLogDto.getHours(), savedDto.getHours());
        assertEquals(workLogDto.getTopic(), savedDto.getTopic());
        assertEquals(workLogDto.getDescription(), savedDto.getDescription());
    }

    @Test
    void updateWorkLog() {
        //given
        WorkLogDto workLogDto = new WorkLogDto();
        workLogDto.setId(workLog1.getId());
        workLogDto.setDate(workLog1.getDate());
        workLogDto.setHours(workLog1.getHours());
        workLogDto.setTopic(workLog1.getTopic());
        workLogDto.setDescription("New description");

        when(workLogRepository.save(any(WorkLog.class))).thenReturn(workLog1);

        //when
        WorkLogDto savedDto = workLogService.updateWorkLog(ID, workLogDto);

        //then
        assertEquals(workLogDto.getId(), savedDto.getId());
        assertEquals(workLogDto.getDate(), savedDto.getDate());
        assertEquals(workLogDto.getHours(), savedDto.getHours());
        assertEquals(workLogDto.getTopic(), savedDto.getTopic());
        assertNotEquals(workLogDto.getDescription(), savedDto.getDescription());
    }

    @Test
    void deleteWorkLog() {
        workLogRepository.deleteById(ID);

        verify(workLogRepository, times(1)).deleteById(anyLong());
    }
}
