package com.g11.schedule.service;

import com.g11.schedule.dto.request.TeamCreateRequest;
import com.g11.schedule.dto.response.AssigmentManageResponse;
import com.g11.schedule.dto.response.TeamResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Team;

import java.util.List;

public interface TeamService {
    TeamResponse createNewTeam(TeamCreateRequest request);

    List<Account> getAllMemberInTeam(Integer idTeam);

    List<AssigmentManageResponse> getAllAssignmentInTeam(Integer idTeam);
}
