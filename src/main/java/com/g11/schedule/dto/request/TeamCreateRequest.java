package com.g11.schedule.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TeamCreateRequest {
    private String name;
    private List<Integer> user;
}
