package com.g11.schedule.exception.Team;

import com.g11.schedule.exception.base.AccessDeniedException;

public class MemberNotInTeamException extends AccessDeniedException {
    public MemberNotInTeamException(){setMessage("member is not in Team");}
}
