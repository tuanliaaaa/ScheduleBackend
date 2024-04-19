package com.g11.schedule.dto.response;

import com.g11.schedule.entity.Team;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssigmentManageResponse {
    private int idAssigment;
    private String nameAssignment;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private List<AssigmentUserResponse> userStatus;
}
