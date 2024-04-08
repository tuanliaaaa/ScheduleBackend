package com.g11.schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCost;
    @ManyToOne
    @JoinColumn(name = "assigment_id")
    private Assigment assigment;
    private String costName;
    private LocalDate refundDay;
    private Integer price;
}
