package com.g11.schedule.exception.User;

import com.g11.schedule.exception.base.NotFoundException;

public class UsernameNotFoundException extends NotFoundException {
    public UsernameNotFoundException(){
        setMessage("User not found");
    }
}
