package com.g11.schedule.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentResponse {
    private String teamName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private String status;
}
