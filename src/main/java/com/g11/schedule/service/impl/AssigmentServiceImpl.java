package com.g11.schedule.service.impl;

import com.g11.schedule.dto.ResponseGeneral;
import com.g11.schedule.dto.request.AssigmentUpdateManageRequest;
import com.g11.schedule.dto.request.AssignmentCreateRequest;
import com.g11.schedule.dto.response.AssigmentUpdateManageResponse;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.entity.*;
import com.g11.schedule.exception.Assigment.AssigmentNotFoundException;
import com.g11.schedule.exception.Assigment.AssigmentNotOfUserException;
import com.g11.schedule.exception.Team.MemberNotInTeamException;
import com.g11.schedule.exception.Team.TeamNotFoundException;
import com.g11.schedule.exception.User.ListUserEmptyException;
import com.g11.schedule.exception.User.UserAccessDeniedException;
import com.g11.schedule.exception.User.UsernameNotFoundException;
import com.g11.schedule.exception.base.AccessDeniedException;
import com.g11.schedule.exception.base.NotFoundException;
import com.g11.schedule.repository.*;
import com.g11.schedule.service.AssigmentService;
import com.g11.schedule.utils.MapperUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final TeamRepository teamRepository;
    @Override
    public AssignmentResponse createNewAssignment(AssignmentCreateRequest newAssigment,Integer idTeam) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException();
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account user = accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Team team = teamRepository.findByIdTeam(idTeam).orElseThrow(TeamNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(user,team);
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
            throw  new UserAccessDeniedException();
        }
        Assigment assigment = new Assigment();
        assigment.setNameAssignment(newAssigment.getNameAssignment());
        assigment.setTeam(team);
        assigment.setDescription(newAssigment.getDescription());
        assigment.setStartAt(newAssigment.getStartAt());
        assigment.setEndAt(newAssigment.getEndAt());



        List<AssigmentUser> assigmentUserList = new ArrayList<>();
        for (Integer idUser : newAssigment.getUsersId()){
            Optional<Account> memberOptional = accountRepository.findById(idUser);
            if (memberOptional.isPresent()) {
                if (!participantRepository.findByUserAndTeam(memberOptional.get(), team).isEmpty()){
                    Account account = memberOptional.get();
                    AssigmentUser assigmentUser = new AssigmentUser();
                    assigmentUser.setAssigment(assigment);
                    assigmentUser.setUser(account);
                    assigmentUser.setStatus(newAssigment.getStatus());
                    assigmentUser.setProcess(newAssigment.getProcess());
                    assigmentUserList.add(assigmentUser);

                }
            }
        }
        if(assigmentUserList.size()==0)throw  new ListUserEmptyException();
        assigment = assigmentRepository.save(assigment);
        assigmentUserRepository.saveAll(assigmentUserList);


        //return
        AssignmentResponse assignmentResponse = new AssignmentResponse();
        assignmentResponse.setTeamName(team.getTeamName());
        assignmentResponse.setStartAt(assigment.getStartAt());
        assignmentResponse.setEndAt(assigment.getEndAt());
        assignmentResponse.setDescription(assigment.getDescription());


        return assignmentResponse;
    }
    @Override
    public List<AssignmentOfUserResponse> getAssignmentOfUser(Integer idTeam){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException();
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Team team = teamRepository.findByIdTeam(idTeam).orElseThrow(TeamNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,team);
        if(participantList.size()==0){
            throw new MemberNotInTeamException();
        }

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
            assignmentResponse.setIdAssigment(assigment.getIdAssigment());
            assignmentResponse.setNameAssignment(assignmentResponse.getNameAssignment());
            list.add(assignmentResponse);
        }
        return list;

    }

    @Override
    public AssignmentResponse getAssignmentById(Integer idAssigment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw  new AccessDeniedException();
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account user = accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Assigment assigment = assigmentRepository.findById(idAssigment).orElseThrow(AssigmentNotFoundException::new);
        List<AssigmentUser> assigmentUserList =assigmentUserRepository.findByUserAndAssigment(user,assigment);
        if(assigmentUserList.size()==0){
            throw new AssigmentNotOfUserException();
        }
        //return
        AssignmentResponse assignmentResponse = new AssignmentResponse();
        assignmentResponse.setDescription(assigment.getDescription());
        assignmentResponse.setStartAt(assigment.getStartAt());
        assignmentResponse.setEndAt(assigment.getEndAt());
        assignmentResponse.setTeamName(assigment.getTeam().getTeamName());
        assignmentResponse.setStatus(assigmentUserList.get(0).getStatus());
        return assignmentResponse;
    }

    @Override
    public AssigmentUpdateManageResponse updateAssignmentManage(AssigmentUpdateManageRequest assigmentUpdateManageRequest, Integer idAssigment){
      Assigment assigment = assigmentRepository.findById(idAssigment).orElseThrow(AssigmentNotFoundException::new);
      if(assigmentUpdateManageRequest.getStartAt() !=null){
          assigment.setStartAt(assigmentUpdateManageRequest.getStartAt());
      }
      if(assigmentUpdateManageRequest.getEndAt()!=null){
          assigment.setEndAt(assigmentUpdateManageRequest.getEndAt());
      }if(assigmentUpdateManageRequest.getDescription()!=null){
          assigment.setDescription(assigmentUpdateManageRequest.getDescription());
      }if(assigmentUpdateManageRequest.getNameAssignment()!=null){
            assigment.setDescription(assigmentUpdateManageRequest.getNameAssignment());
      }

      assigmentRepository.save(assigment);
      return  new AssigmentUpdateManageResponse (assigment);
    };

}
