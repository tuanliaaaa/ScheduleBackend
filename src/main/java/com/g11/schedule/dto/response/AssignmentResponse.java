package com.g11.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponse {
    private Integer idAssignment;
    private String teamName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private String status;
    private String assingmentName;
}
