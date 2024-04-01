package com.g11.schedule.exception.Assigment;

import com.g11.schedule.exception.base.AccessDeniedException;

public class AssigmentNotOfUserException extends AccessDeniedException {
    public AssigmentNotOfUserException(){setMessage("assigment not of you");}
}
