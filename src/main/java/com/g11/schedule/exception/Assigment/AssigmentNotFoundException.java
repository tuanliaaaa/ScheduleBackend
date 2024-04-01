package com.g11.schedule.exception.Assigment;

import com.g11.schedule.exception.base.NotFoundException;

public class AssigmentNotFoundException extends NotFoundException {
    public AssigmentNotFoundException(){setMessage("Assigment not found");}
}
