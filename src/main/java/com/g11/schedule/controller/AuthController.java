package com.g11.schedule.controller;

import com.g11.schedule.dto.ResponseGeneral;
import com.g11.schedule.dto.request.LoginRequest;
import com.g11.schedule.dto.response.LoginResponse;
import com.g11.schedule.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;


    @PostMapping("login")
    public ResponseEntity<ResponseGeneral<LoginResponse>> login(@Valid  @RequestBody LoginRequest loginRequest){
        System.out.println(new BCryptPasswordEncoder().encode("amdin"));
        ResponseGeneral<LoginResponse> responseGeneral=ResponseGeneral.ofCreated("success", accountService.login(loginRequest));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }
//    @PostMapping("register")
//    public ResponseGeneral<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
// System.out.println(new BCryptPasswordEncoder().encode("amdin"));
//        return ResponseGeneral.ofCreated(
//                SUCCESS,
//                userService.register(registerRequest)
//        );
//    }

}