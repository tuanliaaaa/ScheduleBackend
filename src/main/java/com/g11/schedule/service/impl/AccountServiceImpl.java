package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.LoginRequest;
import com.g11.schedule.dto.response.InforResponse;
import com.g11.schedule.dto.response.LoginResponse;
import com.g11.schedule.dto.response.RoleResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Participant;
import com.g11.schedule.entity.Role;
import com.g11.schedule.entity.Team;
import com.g11.schedule.exception.Team.TeamNotFoundException;
import com.g11.schedule.exception.base.AccessDeniedException;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.repository.ParticipantRepository;
import com.g11.schedule.repository.TeamRepository;
import com.g11.schedule.security.JwtUtilities;
import com.g11.schedule.service.AccountService;
import  com.g11.schedule.exception.User.UsernameNotFoundException;
import com.g11.schedule.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final JwtUtilities jwtUtilities;

    @Autowired
    private FileService fileService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


            Account user = accountRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException());
            List<String> rolesNames = accountRepository.findRolesByUsername(user.getUsername());
            String token = jwtUtilities.generateToken(user.getUsername(), rolesNames);
            return new LoginResponse(token);
        } catch (Exception ex) {
            throw new UsernameNotFoundException();
        }
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
    }
    @Override
    public InforResponse infor(Integer idTeam) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException();
        }
        String username =  (String) authentication.getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        InforResponse inforResponse = new InforResponse();
        Team team=teamRepository.findByIdTeam(idTeam).orElseThrow(TeamNotFoundException::new);
        List<Participant> participants= participantRepository.findByUserAndTeam(account,team);
        inforResponse.setIduser(account.getIdUser());
        inforResponse.setPotition(participants.get(0).getPosition());
        return inforResponse;
    }

    @Override
    public List<Account> findAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<Account> response = new ArrayList<>();
        for (Account account : accounts){
            account.setAvatar(fileService.getPhotoURL(account.getAvatar()));
            response.add(account);
        }
        return response;
    }
}