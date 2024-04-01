package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.AssignmentUpdateRequest;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.AssigmentUser;
import com.g11.schedule.exception.Assigment.AssigmentNotFoundException;
import com.g11.schedule.exception.Assigment.AssigmentNotOfUserException;
import com.g11.schedule.exception.User.UsernameNotFoundException;
import com.g11.schedule.exception.base.AccessDeniedException;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.repository.AssigmentRepository;
import com.g11.schedule.repository.AssigmentUserRepository;
import com.g11.schedule.service.AssigmentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssigmentUserServiceImpl implements AssigmentUserService {
    private final AssigmentUserRepository assigmentUserRepository;
    private final AssigmentRepository assigmentRepository;
    private final AccountRepository accountRepository;


    @Override
    public AssignmentOfUserResponse updateAssignmentByUser(AssignmentUpdateRequest assignmentUpdateRequest,Integer idAssigment) {
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
        assigmentUserList.get(0).setProcess(assignmentUpdateRequest.getProcess());
        assigmentUserRepository.save(assigmentUserList.get(0));

        AssignmentOfUserResponse assignmentResponse = new AssignmentOfUserResponse();
        assignmentResponse.setDescription(assigmentUserList.get(0).getAssigment().getDescription());
        assignmentResponse.setStartAt(assigmentUserList.get(0).getAssigment().getStartAt());
        assignmentResponse.setEndAt(assigmentUserList.get(0).getAssigment().getEndAt());
        assignmentResponse.setStatus(assigmentUserList.get(0).getStatus());
        assignmentResponse.setProcess(assigmentUserList.get(0).getProcess());

        return assignmentResponse;
    }
}
