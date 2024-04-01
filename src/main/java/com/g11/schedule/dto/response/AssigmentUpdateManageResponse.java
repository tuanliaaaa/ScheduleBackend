package com.g11.schedule.dto.response;

import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.AssigmentUser;
import com.g11.schedule.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssigmentUpdateManageResponse {
    private int idAssigment;
    private Team team;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    public AssigmentUpdateManageResponse(Assigment assigment){
        this.idAssigment=assigment.getIdAssigment();
        this.team=assigment.getTeam();
        this.startAt=assigment.getStartAt();
        this.endAt=assigment.getEndAt();
        this.description=assigment.getDescription();
    }
}
