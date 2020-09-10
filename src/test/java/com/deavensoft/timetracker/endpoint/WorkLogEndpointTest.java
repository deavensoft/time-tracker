package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.mapper.WorkLogMapper;
import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.WorkLog;
import com.deavensoft.timetracker.service.WorkLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkLogEndpointTest {
    public static final String BASE_URL = "/v1.0/worklogs";

    @Mock
    private WorkLogService workLogService;

    @Mock
    private WorkLogMapper mapper;

    @InjectMocks
    private WorkLogEndpoint workLogEndpoint;

    private MockMvc mockMvc;
    private WorkLogDto workLogDto;
    private WorkLog returnedWorkLog;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(workLogEndpoint).build();

        workLogDto = new WorkLogDto();
        workLogDto.setId(1L);
        workLogDto.setDate(LocalDate.parse("2020-09-09"));
        workLogDto.setHours(5.5);
        workLogDto.setTopic("topic 1");
        workLogDto.setDescription("description 1");

        returnedWorkLog = new WorkLog();
        returnedWorkLog.setId(1L);
        returnedWorkLog.setDate(LocalDate.parse("2020-09-09"));
        returnedWorkLog.setHours(5.5);
        returnedWorkLog.setTopic("topic 1");
        returnedWorkLog.setDescription("description 1");
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
                .content(new ObjectMapper().writeValueAsString(workLogDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workLogDto.getId()))
                .andExpect(jsonPath("$.hours", equalTo(workLogDto.getHours())))
                .andExpect(jsonPath("$.topic", equalTo(workLogDto.getTopic())))
                .andExpect(jsonPath("$.description", equalTo(workLogDto.getDescription())));
    }

    @Test
    void updateWorkLogTest() throws Exception {
        when(workLogService.updateWorkLog(anyLong(), any())).thenReturn(returnedWorkLog);
        when(mapper.workLogToWorkLogDto(any())).thenReturn(workLogDto);

        mockMvc.perform(put(BASE_URL + "/" + returnedWorkLog.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workLogDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workLogDto.getId()))
                .andExpect(jsonPath("$.hours", equalTo(workLogDto.getHours())))
                .andExpect(jsonPath("$.topic", equalTo(workLogDto.getTopic())))
                .andExpect(jsonPath("$.description", equalTo(workLogDto.getDescription())));
    }

    @Test
    void deleteWorkLogById(){
        workLogService.deleteWorkLog(workLogDto.getId());

        verify(workLogService, times(1)).deleteWorkLog(anyLong());
    }

}
