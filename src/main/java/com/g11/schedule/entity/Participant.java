package com.g11.schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPaticipant;
    private String position;
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "idTeam")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "idUser")
    private Account user;
}
