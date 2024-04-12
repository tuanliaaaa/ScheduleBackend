package com.g11.schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class Assigment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAssigment;
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "idTeam")
    private Team team;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;

}
