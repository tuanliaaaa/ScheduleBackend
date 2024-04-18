package com.g11.schedule.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssigmentUpdateManageRequest {
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private String nameAssignment;
}
