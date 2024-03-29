package com.g11.schedule.exception.User;

import com.g11.schedule.exception.base.AccessDeniedException;

public class UserAccessDeniedException extends AccessDeniedException {
    public UserAccessDeniedException(){
        setMessage("User Access Denied ");
    }
}
