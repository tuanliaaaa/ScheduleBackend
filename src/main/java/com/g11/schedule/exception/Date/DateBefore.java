package com.g11.schedule.exception.Date;

import com.g11.schedule.exception.base.BadRequestException;

public class DateBefore extends BadRequestException {
    public DateBefore(){setMessage("startAt must before endAt");}

}
