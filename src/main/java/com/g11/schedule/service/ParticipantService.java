package com.g11.schedule.service;

import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Participant;
import com.g11.schedule.entity.Team;

public interface ParticipantService {
    Boolean checkManage(Team team, Account account);

    Boolean checkIfUserInTeam(Team team, Account account);
}
