package com.g11.schedule.service;

import com.g11.schedule.dto.request.LoginRequest;
import com.g11.schedule.dto.response.LoginResponse;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest) ;

}
