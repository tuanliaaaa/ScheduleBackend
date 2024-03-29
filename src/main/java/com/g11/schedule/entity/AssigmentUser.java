package com.g11.schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AssigmentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAssigmentUser;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account user;
    @ManyToOne
    @JoinColumn(name = "assigment_id")
    private Assigment assigment;
    private String status;
    private String process;

}

