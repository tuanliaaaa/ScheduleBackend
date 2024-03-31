package com.g11.schedule.controller;

import com.g11.schedule.dto.ResponseGeneral;
import com.g11.schedule.dto.request.AssignmentCreateRequest;
import com.g11.schedule.dto.request.AssignmentUpdateRequest;
import com.g11.schedule.dto.response.AssignmentOfUserResponse;
import com.g11.schedule.dto.response.AssignmentResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.AssigmentUser;
import com.g11.schedule.entity.Team;
import com.g11.schedule.exception.User.UserAccessDeniedException;
import com.g11.schedule.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/Assignment")
public class AssignmentController {
    private final TeamService teamService;
    private final AccountService accountService;
    private final ParticipantService participantService;
    private final AssigmentService assigmentService;
    private final AssigmentUserService assigmentUserService;

    @PostMapping("/Team/{idTeam}")
    public ResponseEntity<?> newTeam(@RequestBody AssignmentCreateRequest assignmentCreateRequest, @PathVariable Integer idTeam){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseGeneral.of(HttpStatus.UNAUTHORIZED.value(), "Không có thông tin xác thực", null));
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //check manage
        Account user = accountService.findAccountByUsername(username);
        Team team = teamService.findById(idTeam);
        if (!participantService.checkManage(team, user)){
            throw new UserAccessDeniedException();
        }
        AssignmentResponse assignmentResponse = assigmentService.createNewAssignment(assignmentCreateRequest, team);
        return new ResponseEntity<>(ResponseGeneral.ofCreated("success", assignmentResponse), HttpStatus.CREATED);
    }

    @GetMapping("/AssigmentLogin/Team/{idTeam}")
    public ResponseEntity<?> getAssignmentByLoginUser(@PathVariable Integer idTeam){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseGeneral.of(HttpStatus.UNAUTHORIZED.value(), "Không có thông tin xác thực", null));
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Team team = teamService.findById(idTeam);
        Account user = accountService.findAccountByUsername(username);

        //check if in team
        if (!participantService.checkIfUserInTeam(team, user)){
            return new ResponseEntity<>(ResponseGeneral.of(400,"member's not in team " + team.getTeamName(), "no_data"), HttpStatus.BAD_REQUEST);
        }

        List<AssignmentOfUserResponse> list = assigmentService.getAssignmentOfUser(team,user);
        return new ResponseEntity<>(ResponseGeneral.of(200,"success",list), HttpStatus.OK);
    }

    @GetMapping("/AssigmentUser/{idAssigment}")
    public ResponseEntity<?>getAssignment(@PathVariable Integer idAssigment){
        Assigment assigment = assigmentService.getAssignmentById(idAssigment);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseGeneral.of(HttpStatus.UNAUTHORIZED.value(), "Không có thông tin xác thực", null));
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Team team = assigment.getTeam();
        Account user = accountService.findAccountByUsername(username);

        //check if in team
        if (!participantService.checkIfUserInTeam(team, user)){
            return new ResponseEntity<>(ResponseGeneral.of(400,"member's not in team " + team.getTeamName(), "no_data"), HttpStatus.BAD_REQUEST);
        }


        //return
        AssignmentResponse assignmentResponse = new AssignmentResponse();
        assignmentResponse.setDescription(assigment.getDescription());
        assignmentResponse.setStartAt(assigment.getStartAt());
        assignmentResponse.setEndAt(assigment.getEndAt());
        assignmentResponse.setTeamName(team.getTeamName());
        return new ResponseEntity<>(ResponseGeneral.of(201,"success", assignmentResponse), HttpStatus.OK);
    }

    @PatchMapping("/AssigmentUser/{idAssigment}")
    public ResponseEntity<?> updateAssignmentStatus(@RequestBody AssignmentUpdateRequest request, @PathVariable Integer idAssigment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseGeneral.of(HttpStatus.UNAUTHORIZED.value(),
                            "Unauthorized", null));
        }

        // Retrieve current user
        String username = (String) authentication.getPrincipal();
        Account user = accountService.findAccountByUsername(username);

        // Check if the user is assigned to the assignment
        AssigmentUser assignmentUser = assigmentUserService.getByAssignmentIdAndUserId(idAssigment, user.getIdUser());
        if (assignmentUser == null) {
            return new ResponseEntity<>(ResponseGeneral.of(HttpStatus.FORBIDDEN.value(),
                    "User is not assigned to this assignment", null), HttpStatus.FORBIDDEN);
        }

        // Check if assignment exists
        Assigment assignment = assigmentService.getAssignmentById(idAssigment);

        // Check if assignment is overdue
        if (assignment.getEndAt().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(ResponseGeneral.of(HttpStatus.BAD_REQUEST.value(),
                    "Assignment is overdue", null), HttpStatus.FORBIDDEN);
        }


        // Update assignment process
        AssignmentOfUserResponse response = assigmentUserService.updateAssignment(assignmentUser, request.getProcess());

        return new ResponseEntity<>(ResponseGeneral.of(HttpStatus.OK.value(),
                "Assignment status updated successfully", response), HttpStatus.OK);
    }

    
}
