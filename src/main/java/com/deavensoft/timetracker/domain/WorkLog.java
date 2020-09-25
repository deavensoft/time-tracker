package com.deavensoft.timetracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Data
public class WorkLog {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Double hours;
    private String topic;
    private String description;

    @ManyToOne
    private User user;
}
