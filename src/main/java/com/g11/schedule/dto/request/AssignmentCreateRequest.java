package com.g11.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AssignmentCreateRequest {

    @NotNull(message = "name is not mandatory")
    private String nameAssignment;

    @NotNull(message = "startAt is not mandatory")
    private LocalDateTime startAt;

    @NotNull(message = "endAt is not mandatory")
    private LocalDateTime endAt;

    @NotBlank(message = "description is not mandatory")
    private String description;


    private String status = "assigned";

    private String process = "0";
    @NotEmpty(message = "user is not mandatory")
    private List<Integer> usersId;
}
