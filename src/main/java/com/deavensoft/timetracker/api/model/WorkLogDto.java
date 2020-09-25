package com.deavensoft.timetracker.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkLogDto {
    private Long id;
    private LocalDate date;
    private Double hours;
    private String topic;
    private String description;

    @JsonProperty("user_id")
    private Long userId;
}
