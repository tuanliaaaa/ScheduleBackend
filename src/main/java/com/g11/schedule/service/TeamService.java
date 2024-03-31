package com.g11.schedule.service;

import com.g11.schedule.dto.request.TeamCreateRequest;
import com.g11.schedule.dto.response.TeamResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Team;

import java.util.List;

public interface TeamService {
    TeamResponse createNewTeam(TeamCreateRequest request, Account account);

    Team findById(Integer id);
}
