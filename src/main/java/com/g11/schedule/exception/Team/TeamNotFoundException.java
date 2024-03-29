package com.g11.schedule.exception.Team;

import com.g11.schedule.exception.base.NotFoundException;

public class TeamNotFoundException extends NotFoundException {
    public TeamNotFoundException(){setMessage("Team not found");}
}
