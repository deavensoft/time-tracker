package com.deavensoft.timetracker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class WorkLog {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double hours;

    private String topic;
    private String description;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Project project;

}
