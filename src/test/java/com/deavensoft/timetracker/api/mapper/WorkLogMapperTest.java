package com.deavensoft.timetracker.api.mapper;

import com.deavensoft.timetracker.api.model.WorkLogDto;
import com.deavensoft.timetracker.domain.WorkLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorkLogMapperTest {

    private Long ID = 1l;
    private LocalDate DATE = LocalDate.now();
    private Double HOURS = 7.5;
    public String TOPIC = "topic";
    public String DESCRIPTION = "description";

    WorkLogMapper mapper = WorkLogMapper.INSTANCE;

    @Test
    void workLogToWorkLogDto() {
        //given
        WorkLog workLog = new WorkLog();
        workLog.setId(ID);
        workLog.setDate(DATE);
        workLog.setHours(HOURS);
        workLog.setTopic(TOPIC);
        workLog.setDescription(DESCRIPTION);

        //when
        WorkLogDto workLogDto = mapper.workLogToWorkLogDto(workLog);

        //then
        assertNotNull(workLogDto);
        assertEquals(ID, workLogDto.getId());
        assertEquals(DATE, workLogDto.getDate());
        assertEquals(HOURS, workLogDto.getHours());
        assertEquals(TOPIC, workLogDto.getTopic());
        assertEquals(DESCRIPTION, workLogDto.getDescription());
    }

    @Test
    void workLogDtoToWorkLog() {
        //given
        WorkLogDto workLogDto = new WorkLogDto();
        workLogDto.setId(ID);
        workLogDto.setDate(DATE);
        workLogDto.setHours(HOURS);
        workLogDto.setTopic(TOPIC);
        workLogDto.setDescription(DESCRIPTION);

        //when
        WorkLog workLog = mapper.workLogDtoToWorkLog(workLogDto);

        //then
        assertNotNull(workLog);
        assertEquals(ID, workLog.getId());
        assertEquals(DATE, workLog.getDate());
        assertEquals(HOURS, workLog.getHours());
        assertEquals(TOPIC, workLog.getTopic());
        assertEquals(DESCRIPTION, workLog.getDescription());
    }
}
