package com.g11.schedule.service.impl;

import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.AssigmentUser;
import com.g11.schedule.repository.AssigmentRepository;
import com.g11.schedule.repository.AssigmentUserRepository;
import com.g11.schedule.service.AssigmentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssigmentUserServiceImpl implements AssigmentUserService {
    private final AssigmentUserRepository assigmentUserRepository;
    private final AssigmentRepository assigmentRepository;
    @Override
    public AssigmentUser getByAssignmentIdAndUserId(Integer assignmentId, Integer userId) {
        return assigmentUserRepository.findAssigmentUserByAssigmentAndUser(assignmentId, userId);
    }

    @Override
    public AssignmentOfUserResponse updateAssignment(AssigmentUser assignmentUser, String process) {
        assignmentUser.setProcess(process);
        assigmentUserRepository.save(assignmentUser);

        AssignmentOfUserResponse assignmentResponse = new AssignmentOfUserResponse();
        assignmentResponse.setDescription(assignmentUser.getAssigment().getDescription());
        assignmentResponse.setStartAt(assignmentUser.getAssigment().getStartAt());
        assignmentResponse.setEndAt(assignmentUser.getAssigment().getEndAt());
        assignmentResponse.setStatus(assignmentUser.getStatus());
        assignmentResponse.setProcess(assignmentUser.getProcess());

        return assignmentResponse;
    }
}
