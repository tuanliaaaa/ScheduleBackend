package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.TeamCreateRequest;
import com.g11.schedule.dto.response.TeamResponse;
import com.g11.schedule.dto.response.UserInTeamResponse;
import com.g11.schedule.entity.Participant;
import com.g11.schedule.entity.Team;
import com.g11.schedule.entity.Account;
import com.g11.schedule.exception.base.NotFoundException;
import com.g11.schedule.repository.ParticipantRepository;
import com.g11.schedule.repository.TeamRepository;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final ParticipantRepository participantsRepository;
    private final AccountRepository accountRepository;


    @Override
    public TeamResponse createNewTeam(TeamCreateRequest request, Account creator) {
        // Tạo và trả về response
        TeamResponse response = new TeamResponse();
        List<UserInTeamResponse> usersInTeamResponse = new ArrayList<>();//response thành viên trong nhóm

        Team team = new Team();
        team.setTeamName(request.getName());
        Team savedTeam = teamRepository.save(team);


        // Tạo đối tượng Participants cho người dùng tạo nhóm
        Participant creatorParticipant = new Participant();


        creatorParticipant.setUser(creator);
        creatorParticipant.setTeam(savedTeam);
        creatorParticipant.setPosition("manage");
        participantsRepository.save(creatorParticipant);
        usersInTeamResponse.add(new UserInTeamResponse(creatorParticipant.getUser().getUsername(),
                creatorParticipant.getPosition()));


        // Tạo đối tượng Participants cho các thành viên khác
        List<Participant> members = new ArrayList<>();
        for (Integer memberId : request.getUser()) {
            Optional<Account> memberOptional = accountRepository.findById(memberId);
            if (memberOptional.isPresent()) {
                Account member = memberOptional.get();
                Participant memberParticipant = new Participant();
                memberParticipant.setUser(member);
                memberParticipant.setTeam(savedTeam);
                memberParticipant.setPosition("user");
                members.add(memberParticipant);

                usersInTeamResponse.add(new UserInTeamResponse(memberParticipant.getUser().getUsername(),
                        memberParticipant.getPosition()));
            }
            else {
                throw new NotFoundException(memberId.toString(), "member");
            }
        }
        participantsRepository.saveAll(members);

        //return
        response.setTeamName(savedTeam.getTeamName());
        response.setUserInTeamResponse(usersInTeamResponse);
        return response;
    }

    @Override
    public Team findById(Integer id) {
        return teamRepository.findById(id).orElseThrow();
    }
}
