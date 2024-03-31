package com.g11.schedule.service;

import com.g11.schedule.dto.request.LoginRequest;
import com.g11.schedule.dto.response.LoginResponse;
import com.g11.schedule.entity.Account;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest) ;

    Account findAccountByUsername(String username);
}
