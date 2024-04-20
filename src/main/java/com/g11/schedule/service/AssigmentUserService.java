package com.g11.schedule.service;

import com.g11.schedule.dto.request.AssigmentUpdateManageRequest;
import com.g11.schedule.dto.request.AssignmentUpdateRequest;
import com.g11.schedule.dto.response.AssigmentManageResponse;
import com.g11.schedule.dto.response.AssigmentUpdateManageResponse;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.StatusAssigmentOfTeamManageResponse;
import com.g11.schedule.entity.AssigmentUser;

public interface AssigmentUserService {
    AssigmentManageResponse getAssigmentManage(Integer assigmentID);
    AssignmentOfUserResponse updateAssignmentByUser(AssignmentUpdateRequest assignmentUpdateRequest,Integer idAssigment);
    StatusAssigmentOfTeamManageResponse statusAssigmentOfTeamManageResponse(Integer idTeam);
}
