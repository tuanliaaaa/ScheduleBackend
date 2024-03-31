package com.g11.schedule.service;

import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.entity.AssigmentUser;

public interface AssigmentUserService {
    AssigmentUser getByAssignmentIdAndUserId(Integer assignmentId, Integer userId);

    AssignmentOfUserResponse updateAssignment(AssigmentUser assignmentUser, String process);
}
