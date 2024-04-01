package com.g11.schedule.controller;

import com.g11.schedule.dto.ResponseGeneral;
import com.g11.schedule.dto.request.AssigmentUpdateManageRequest;
import com.g11.schedule.dto.request.AssignmentCreateRequest;
import com.g11.schedule.dto.request.AssignmentUpdateRequest;
import com.g11.schedule.dto.response.AssigmentManageResponse;
import com.g11.schedule.dto.response.AssigmentUpdateManageResponse;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/Assignment")
public class AssignmentController {

    private final AssigmentService assigmentService;
    private final AssigmentUserService assigmentUserService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/Team/{idTeam}")
    public ResponseEntity<?> newAssigment(@Valid @RequestBody AssignmentCreateRequest assignmentCreateRequest, @PathVariable Integer idTeam){
        AssignmentResponse assignmentResponse = assigmentService.createNewAssignment(assignmentCreateRequest,idTeam);
        return new ResponseEntity<>(ResponseGeneral.ofCreated("success", assignmentResponse), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/AssigmentLogin/Team/{idTeam}")
    public ResponseEntity<?> getAssignmentByLoginUser(@PathVariable Integer idTeam){
        List<AssignmentOfUserResponse> list = assigmentService.getAssignmentOfUser(idTeam);
        return new ResponseEntity<>(ResponseGeneral.of(200,"success",list), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/AssigmentUser/{idAssigment}")
    public ResponseEntity<?>getAssignmentByID(@PathVariable Integer idAssigment){
        return new ResponseEntity<>(ResponseGeneral.of(200,"success", assigmentService.getAssignmentById(idAssigment)), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/AssigmentUser/{idAssigment}")
    public ResponseEntity<?> updateAssignmentStatus(@RequestBody AssignmentUpdateRequest request, @PathVariable Integer idAssigment) {
        AssignmentOfUserResponse response = assigmentUserService.updateAssignmentByUser(request,idAssigment);

        return new ResponseEntity<>(ResponseGeneral.of(HttpStatus.OK.value(),
                "Assignment status updated successfully", response), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/AssigmentManage/{idAssigment}")
    public ResponseEntity<?> getAssignmentManage(@PathVariable Integer idAssigment) {
        AssigmentManageResponse response = assigmentUserService.getAssigmentManage(idAssigment);

        return new ResponseEntity<>(ResponseGeneral.of(HttpStatus.OK.value(),
                "Assignment status updated successfully", response), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/AssigmentManage/{idAssigment}")
    public ResponseEntity<?> updateAssignmentManage(@RequestBody AssigmentUpdateManageRequest assigmentUpdateManageRequest, @PathVariable Integer idAssigment) {
        AssigmentUpdateManageResponse response = assigmentService.updateAssignmentManage(assigmentUpdateManageRequest,idAssigment);

        return new ResponseEntity<>(ResponseGeneral.of(HttpStatus.OK.value(),
                "Assignment status updated successfully", response), HttpStatus.OK);
    }
}
