package com.g11.schedule.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TeamResponse {
    private String teamName;

    private List<UserInTeamResponse> userInTeamResponse;
    private Integer costExpected;
}
