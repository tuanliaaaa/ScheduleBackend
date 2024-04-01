package com.g11.schedule.controller;

import com.g11.schedule.dto.ResponseGeneral;
import com.g11.schedule.dto.request.TeamCreateRequest;
import com.g11.schedule.dto.response.TeamResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.service.TeamService;
import com.g11.schedule.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("team")
public class TeamController {
    private final TeamService teamService;
    private final AccountService accountService;

    public TeamController(TeamService teamService, AccountService accountService) {
        this.teamService = teamService;
        this.accountService = accountService;
    }

    @PostMapping("")
    public ResponseEntity<?> newTeam(@RequestBody TeamCreateRequest teamCreateRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseGeneral.of(HttpStatus.UNAUTHORIZED.value(), "Không có thông tin xác thực", null));
        }
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account creator = accountService.findAccountByUsername(username);
        TeamResponse teamResponse = teamService.createNewTeam(teamCreateRequest,creator);
        return new ResponseEntity<>(ResponseGeneral.ofCreated("success", teamResponse), HttpStatus.CREATED);
    }
}
