package com.g11.schedule.controller;

import com.g11.schedule.dto.ResponseGeneral;
import com.g11.schedule.dto.request.TeamCreateRequest;
import com.g11.schedule.dto.response.TeamResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.service.TeamService;
import com.g11.schedule.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
@Slf4j
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;


    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> newTeam(@Valid @RequestBody TeamCreateRequest teamCreateRequest){
        TeamResponse teamResponse = teamService.createNewTeam(teamCreateRequest);
        return new ResponseEntity<>(ResponseGeneral.ofCreated("success", teamResponse), HttpStatus.CREATED);
    }
}
