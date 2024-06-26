package com.g11.schedule.service;

import com.g11.schedule.dto.request.AssigmentUpdateManageRequest;
import com.g11.schedule.dto.request.AssignmentCreateRequest;
import com.g11.schedule.dto.response.AssigmentManageResponse;
import com.g11.schedule.dto.response.AssigmentUpdateManageResponse;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.Team;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AssigmentService {
    AssignmentResponse createNewAssignment(AssignmentCreateRequest assigment,Integer idTeam);
    List<AssignmentOfUserResponse> getAssignmentOfUser(Integer idTeam);


    AssignmentResponse getAssignmentById(Integer id);
    AssigmentUpdateManageResponse updateAssignmentManage(AssigmentUpdateManageRequest assigmentUpdateManageRequest, Integer idAssigment);

    List<AssignmentResponse> getAllAssignmentInTeam(Integer idTeam);
}
