package com.g11.schedule.exception.User;

import com.g11.schedule.exception.base.NotFoundException;

public class ListUserEmptyException extends NotFoundException {
    public ListUserEmptyException(){setMessage("List user not empty");}
}
