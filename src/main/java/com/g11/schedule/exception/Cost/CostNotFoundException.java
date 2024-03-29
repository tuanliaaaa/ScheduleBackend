package com.g11.schedule.exception.Cost;

import com.g11.schedule.exception.base.NotFoundException;

public class CostNotFoundException extends NotFoundException {
    public CostNotFoundException(){setMessage("Cost not found");}
}
