package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.TeamCreateRequest;
import com.g11.schedule.dto.response.TeamResponse;
import com.g11.schedule.dto.response.UserInTeamResponse;
import com.g11.schedule.entity.Participant;
import com.g11.schedule.entity.Team;
import com.g11.schedule.entity.Account;
import com.g11.schedule.exception.User.ListUserEmptyException;
import com.g11.schedule.exception.User.UsernameNotFoundException;
import com.g11.schedule.exception.base.AccessDeniedException;
import com.g11.schedule.exception.base.NotFoundException;
import com.g11.schedule.repository.ParticipantRepository;
import com.g11.schedule.repository.TeamRepository;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public TeamResponse createNewTeam(TeamCreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException();
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account creator = accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        TeamResponse response = new TeamResponse();
        List<UserInTeamResponse> usersInTeamResponse = new ArrayList<>();//response thành viên trong nhóm

        Team team = new Team();
        team.setTeamName(request.getName());
        team.setCostExpected(request.getCostExpected());


        // Tạo đối tượng Participants cho người dùng tạo nhóm
        Participant creatorParticipant = new Participant();
        creatorParticipant.setUser(creator);
        creatorParticipant.setTeam(team);
        creatorParticipant.setPosition("manage");
//        participantsRepository.save(creatorParticipant);
        usersInTeamResponse.add(new UserInTeamResponse(creatorParticipant.getUser().getUsername(),
                creatorParticipant.getPosition()));
        List<Participant> members = new ArrayList<>();
        for (Integer memberId : request.getUser()) {
            Optional<Account> memberOptional = accountRepository.findById(memberId);
            if (memberOptional.isPresent()) {
                if(memberOptional.get().getIdUser()!=creator.getIdUser())
                {
                    Account member = memberOptional.get();
                    Participant memberParticipant = new Participant();
                    memberParticipant.setUser(member);
                    memberParticipant.setTeam(team);
                    memberParticipant.setPosition("user");
                    members.add(memberParticipant);
                    usersInTeamResponse.add(new UserInTeamResponse(memberParticipant.getUser().getUsername(),
                            memberParticipant.getPosition()));
                }
            }
            else {
                throw new NotFoundException(memberId.toString(), "member");
            }
        }
        if(members.size()==0)throw  new ListUserEmptyException();
        team=teamRepository.save(team);
        participantsRepository.save(creatorParticipant);
        participantsRepository.saveAll(members);

        //return
        response.setTeamName(team.getTeamName());
        response.setUserInTeamResponse(usersInTeamResponse);
        response.setCostExpected(team.getCostExpected());
        return response;
    }


}
