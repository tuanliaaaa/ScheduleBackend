package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.AssignmentUpdateRequest;
import com.g11.schedule.dto.response.AssigmentManageResponse;
import com.g11.schedule.dto.response.AssigmentUserResponse;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.entity.*;
import com.g11.schedule.exception.Assigment.AssigmentNotFoundException;
import com.g11.schedule.exception.Assigment.AssigmentNotOfUserException;
import com.g11.schedule.exception.Team.TeamNotFoundException;
import com.g11.schedule.exception.User.UserAccessDeniedException;
import com.g11.schedule.exception.User.UsernameNotFoundException;
import com.g11.schedule.exception.base.AccessDeniedException;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.repository.AssigmentRepository;
import com.g11.schedule.repository.AssigmentUserRepository;
import com.g11.schedule.repository.ParticipantRepository;
import com.g11.schedule.service.AssigmentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssigmentUserServiceImpl implements AssigmentUserService {
    private final AssigmentUserRepository assigmentUserRepository;
    private final AssigmentRepository assigmentRepository;
    private final AccountRepository accountRepository;
    private final ParticipantRepository participantRepository;


    @Override
    public AssignmentOfUserResponse updateAssignmentByUser(AssignmentUpdateRequest assignmentUpdateRequest,Integer idAssigment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw  new AccessDeniedException();
        }
        String username =  (String) authentication.getPrincipal();
        Account user = accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Assigment assigment = assigmentRepository.findById(idAssigment).orElseThrow(AssigmentNotFoundException::new);
        List<AssigmentUser> assigmentUserList =assigmentUserRepository.findByUserAndAssigment(user,assigment);
        if(assigmentUserList.size()==0){
            throw new AssigmentNotOfUserException();
        }
        assigmentUserList.get(0).setProcess(assignmentUpdateRequest.getProcess());
        assigmentUserRepository.save(assigmentUserList.get(0));

        AssignmentOfUserResponse assignmentResponse = new AssignmentOfUserResponse();
        assignmentResponse.setDescription(assigmentUserList.get(0).getAssigment().getDescription());
        assignmentResponse.setStartAt(assigmentUserList.get(0).getAssigment().getStartAt());
        assignmentResponse.setEndAt(assigmentUserList.get(0).getAssigment().getEndAt());
        assignmentResponse.setStatus(assigmentUserList.get(0).getStatus());
        assignmentResponse.setProcess(assigmentUserList.get(0).getProcess());
        assignmentResponse.setIdAssigment(assigmentUserList.get(0).getAssigment().getIdAssigment());
        return assignmentResponse;
    }

    public AssigmentManageResponse getAssigmentManage(Integer assigmentID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException();
        }
        String username =  (String) authentication.getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Assigment assigment= assigmentRepository.findById(assigmentID).orElseThrow(AssigmentNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,assigment.getTeam());
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
            throw  new UserAccessDeniedException();
        }
        List<AssigmentUser> assigmentUserList=assigmentUserRepository.findByAssigment(assigment);
        List<AssigmentUserResponse> assigmentUserResponseList = new ArrayList<>();
        for (AssigmentUser assigmentUser:assigmentUserList)
        {
            assigmentUserResponseList.add(new AssigmentUserResponse(assigmentUser.getUser().getIdUser(),assigmentUser.getIdAssigmentUser(),assigmentUser.getUser().getUsername(),assigmentUser.getStatus(),assigmentUser.getProcess()));
        }
        return new AssigmentManageResponse(assigment.getIdAssigment(),assigment.getNameAssignment(), assigment.getStartAt(),assigment.getEndAt(),assigment.getDescription(),assigmentUserResponseList);

    };
}
