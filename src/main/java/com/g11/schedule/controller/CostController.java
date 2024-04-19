package com.g11.schedule.controller;


import com.g11.schedule.dto.ResponseGeneral;
import com.g11.schedule.dto.request.CostRequest;
import com.g11.schedule.dto.response.CostResponse;
import com.g11.schedule.dto.response.LoginResponse;
import com.g11.schedule.service.AccountService;
import com.g11.schedule.service.CostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cost")
@Slf4j
@RequiredArgsConstructor
public class CostController {
    private final CostService costService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/Asigment/{idAssigment}")
    public ResponseEntity<ResponseGeneral<List<CostResponse>>> findCostByIDAssigment(@PathVariable("idAssigment") Integer idAssigment){
        ResponseGeneral<List<CostResponse>> responseGeneral= ResponseGeneral.of(200,"success",costService.findCostByIDAssigment(idAssigment));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/Assigment/{idAssigment}")
    public ResponseEntity<ResponseGeneral<CostResponse>> createCostByIDTeam(@Valid @RequestBody CostRequest costRequest,@PathVariable("idAssigment") Integer idAssigment){
        ResponseGeneral<CostResponse> responseGeneral= ResponseGeneral.of(200,"success",costService.saveCost(costRequest,idAssigment));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{idCode}")
    public ResponseEntity<ResponseGeneral<CostResponse>> getCostByIDCost(@PathVariable("idCode") Integer idCode){
        ResponseGeneral<CostResponse> responseGeneral= ResponseGeneral.of(200,"success",costService.getByIDCode(idCode));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/assignment/{idAssignment}")
    public ResponseEntity<ResponseGeneral<List<CostResponse>>> getCostByIdAssignment(@PathVariable("idAssignment") Integer idAssignment){
        ResponseGeneral<List<CostResponse>> responseGeneral= ResponseGeneral.of(200,"success",costService.getByIdAssignment(idAssignment));
        return new ResponseEntity<>(responseGeneral, HttpStatus.OK);
    }
}
