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
    @JoinColumn(name = "user_id", referencedColumnName = "idUser")
    private Account user;
    @ManyToOne
    @JoinColumn(name = "assigment_id", referencedColumnName = "idAssigment")
    private Assigment assigment;
    private String status;
    private String process;

}

