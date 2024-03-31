package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.AssignmentCreateRequest;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.AssigmentUser;
import com.g11.schedule.entity.Team;
import com.g11.schedule.exception.base.NotFoundException;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.repository.AssigmentRepository;
import com.g11.schedule.repository.AssigmentUserRepository;
import com.g11.schedule.repository.ParticipantRepository;
import com.g11.schedule.service.AssigmentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class AssigmentServiceImpl implements AssigmentService {
    private final AccountRepository accountRepository;
    private final AssigmentRepository assigmentRepository;
    private final AssigmentUserRepository assigmentUserRepository;
    private final ParticipantRepository participantRepository;
    @Override
    public AssignmentResponse createNewAssignment(AssignmentCreateRequest newAssigment, Team team) {
        for (Integer idUser : newAssigment.getUsersId()) {
            Optional<Account> memberOptional = accountRepository.findById(idUser);
            if (memberOptional.isPresent()) {
                Account account = memberOptional.get();
                //check member in team
                if (participantRepository.findByUserAndTeam(account, team).isEmpty()) {
                    throw new NotFoundException(idUser.toString(), "in team");
                }
            }
        }
        Assigment assigment = new Assigment();
        assigment.setTeam(team);
        assigment.setDescription(newAssigment.getDescription());
        assigment.setStartAt(newAssigment.getStartAt());
        assigment.setEndAt(newAssigment.getEndAt());
        Assigment savedAssignment = assigmentRepository.save(assigment);


        List<AssigmentUser> assigmentUserList = new ArrayList<>();
        for (Integer idUser : newAssigment.getUsersId()){
            Optional<Account> memberOptional = accountRepository.findById(idUser);
            if (memberOptional.isPresent()) {
                Account account = memberOptional.get();
                    AssigmentUser assigmentUser = new AssigmentUser();
                    assigmentUser.setAssigment(savedAssignment);
                    assigmentUser.setUser(account);
                    assigmentUser.setStatus(newAssigment.getStatus());
                    assigmentUser.setProcess(newAssigment.getProcess());
                    assigmentUserList.add(assigmentUser);

            }else {
                throw new NotFoundException(idUser.toString(), "member");
            }
        }
        assigmentUserRepository.saveAll(assigmentUserList);


        //retun
        AssignmentResponse assignmentResponse = new AssignmentResponse();
        assignmentResponse.setTeamName(team.getTeamName());
        assignmentResponse.setStartAt(savedAssignment.getStartAt());
        assignmentResponse.setEndAt(savedAssignment.getEndAt());
        assignmentResponse.setDescription(savedAssignment.getDescription());


        return assignmentResponse;
    }

    public List<AssignmentOfUserResponse> getAssignmentOfUser(Team team, Account account){
        List<AssignmentOfUserResponse> list = new ArrayList<>();
        List<AssigmentUser> assigmentUserList = assigmentUserRepository.findByUserIdAndTeamId(account.getIdUser(), team.getIdTeam());

        for (AssigmentUser assigmentUser : assigmentUserList){
            Assigment assigment = assigmentUser.getAssigment();
            AssignmentOfUserResponse assignmentResponse = new AssignmentOfUserResponse();
            assignmentResponse.setDescription(assigment.getDescription());
            assignmentResponse.setStatus(assigmentUser.getStatus());
            assignmentResponse.setProcess(assigmentUser.getProcess());
            assignmentResponse.setStartAt(assigment.getStartAt());
            assignmentResponse.setEndAt(assigment.getEndAt());
            list.add(assignmentResponse);
        }

        return list;

    }

    @Override
    public Assigment getAssignmentById(Integer id) {
        return assigmentRepository.findById(id).orElseThrow();
    }
}
