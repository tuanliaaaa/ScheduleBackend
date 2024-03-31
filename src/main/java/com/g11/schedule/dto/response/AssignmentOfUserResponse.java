package com.g11.schedule.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentOfUserResponse {
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private String status;
    private String process;
}
