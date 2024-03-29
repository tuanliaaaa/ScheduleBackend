package com.g11.schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCost;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    private String costName;
    private Integer price;
}
