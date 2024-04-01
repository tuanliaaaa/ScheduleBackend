package com.g11.schedule.service;

import com.g11.schedule.dto.request.AssignmentUpdateRequest;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.entity.AssigmentUser;

public interface AssigmentUserService {

    AssignmentOfUserResponse updateAssignmentByUser(AssignmentUpdateRequest assignmentUpdateRequest,Integer idAssigment);
}
