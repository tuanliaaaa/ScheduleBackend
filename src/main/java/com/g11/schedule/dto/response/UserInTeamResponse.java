package com.g11.schedule.dto.response;

import lombok.Data;

@Data
public class UserInTeamResponse {
    private String userName;

    private String position;

    public UserInTeamResponse(String userName, String position) {
        this.userName = userName;
        this.position = position;
    }
}
