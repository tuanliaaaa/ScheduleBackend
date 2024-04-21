package com.g11.schedule.service;

import com.g11.schedule.dto.request.LoginRequest;
import com.g11.schedule.dto.response.InforResponse;
import com.g11.schedule.dto.response.LoginResponse;
import com.g11.schedule.entity.Account;

import java.util.List;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest) ;

    Account findAccountByUsername(String username);
    InforResponse infor(Integer idTeam);
    List<Account> findAllAccounts();
}
