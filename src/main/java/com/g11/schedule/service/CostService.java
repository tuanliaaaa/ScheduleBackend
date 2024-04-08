package com.g11.schedule.service;

import com.g11.schedule.dto.request.CostRequest;
import com.g11.schedule.dto.response.CostResponse;

import java.util.List;

public interface CostService {
    List<CostResponse> findCostByIDTeam(Integer idTeam);
    CostResponse saveCost(CostRequest costRequest,Integer idTeam);
    CostResponse getByIDCode(Integer idCode);
}
