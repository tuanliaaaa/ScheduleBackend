package com.g11.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TeamCreateRequest {
    @NotBlank(message = "name is not mandatory")
    private String name;
    @NotEmpty(message = "user is not mandatory")
    private List<Integer> user;
    @NotNull(message = "costExpected is not madatory")
    private Integer costExpected;

}
