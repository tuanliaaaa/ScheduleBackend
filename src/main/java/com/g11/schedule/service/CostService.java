package com.g11.schedule.service;

import com.g11.schedule.dto.request.CostRequest;
import com.g11.schedule.dto.response.CostResponse;

import java.time.LocalDate;
import java.util.List;

public interface CostService {
    List<CostResponse> findCostByIDAssigment(Integer idTeam);
    CostResponse saveCost(CostRequest costRequest,Integer idAssigment);
    CostResponse getByIDCode(Integer idCode);

    List<CostResponse> getByIdAssignment(Integer idAssignment);

    List<CostResponse> getCostByOrderByRefundDate(Integer idTeam, LocalDate fromDate, LocalDate toDate);


    CostResponse updateCost(Integer idOldCost, CostRequest costRequest);
}
