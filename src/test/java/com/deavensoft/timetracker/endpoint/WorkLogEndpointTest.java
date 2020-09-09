package com.deavensoft.timetracker.endpoint;

import com.deavensoft.timetracker.api.model.WorkLogDto;
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

    @Mock
    WorkLogService workLogService;

    @InjectMocks
    WorkLogEndpoint workLogEndpoint;

    private MockMvc mockMvc;
    public static final String BASE_URL = "/v1.0/worklogs";
    private WorkLogDto workLogDto;

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
    }

    @Test
    void getWorkLogTest() throws Exception {

        when(workLogService.getWorkLogById(anyLong())).thenReturn(workLogDto);

        mockMvc.perform(get(BASE_URL + "/" + workLogDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic", equalTo(workLogDto.getTopic())));
    }

    @Test
    void getWorkLogsTest() throws Exception {
        WorkLogDto workLogDto1 = new WorkLogDto();
        workLogDto1.setId(1L);
        workLogDto1.setDate(LocalDate.now());
        workLogDto1.setHours(5.5);
        workLogDto1.setTopic("topic 1");
        workLogDto1.setDescription("description 1");

        WorkLogDto workLogDto2 = new WorkLogDto();
        workLogDto2.setId(2L);
        workLogDto2.setDate(LocalDate.now());
        workLogDto2.setHours(3.5);
        workLogDto2.setTopic("topic 2");
        workLogDto2.setDescription("description 2");

        when(workLogService.getAllWorkLogs()).thenReturn(Arrays.asList(workLogDto, workLogDto1, workLogDto2));

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workLogs", hasSize(3)));
    }

    @Test
    void createWorkLogTest() throws Exception {

        when(workLogService.createWorkLog(any())).thenReturn(workLogDto);

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

        when(workLogService.updateWorkLog(anyLong(), any(WorkLogDto.class))).thenReturn(workLogDto);

        mockMvc.perform(put(BASE_URL + "/" + workLogDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(workLogDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workLogDto.getId()))
                .andExpect(jsonPath("$.hours", equalTo(workLogDto.getHours())))
                .andExpect(jsonPath("$.topic", equalTo(workLogDto.getTopic())))
                .andExpect(jsonPath("$.description", equalTo(workLogDto.getDescription())));
    }

    @Test
    void deleteWorkLogById() throws Exception {
        workLogService.deleteWorkLog(workLogDto.getId());

        verify(workLogService, times(1)).deleteWorkLog(anyLong());
    }

}
