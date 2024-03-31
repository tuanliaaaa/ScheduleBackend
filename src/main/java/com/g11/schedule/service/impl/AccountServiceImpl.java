package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.LoginRequest;
import com.g11.schedule.dto.response.LoginResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.security.JwtUtilities;
import com.g11.schedule.service.AccountService;
import  com.g11.schedule.exception.User.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


}