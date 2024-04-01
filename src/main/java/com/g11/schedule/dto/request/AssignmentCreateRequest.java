package com.g11.schedule.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AssignmentCreateRequest {

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String description;
    private String status = "assigned";
    private String process = "0";

    private List<Integer> usersId;
}
