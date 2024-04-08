package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.CostRequest;
import com.g11.schedule.dto.response.CostResponse;
import com.g11.schedule.entity.*;
import com.g11.schedule.exception.Assigment.AssigmentNotFoundException;
import com.g11.schedule.exception.Cost.CostNotFoundException;
import com.g11.schedule.exception.Team.TeamNotFoundException;
import com.g11.schedule.exception.User.UserAccessDeniedException;
import com.g11.schedule.exception.User.UsernameNotFoundException;
import com.g11.schedule.repository.*;
import com.g11.schedule.service.CostService;
import com.g11.schedule.service.TeamService;
import com.g11.schedule.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {
    private final CostRepository costRepository;
    private final AssigmentRepository assigmentRepository;
    private final AccountRepository accountRepository;
    private final ParticipantRepository participantRepository;
    @Override
    public List<CostResponse> findCostByIDAssigment(Integer idAssigment){
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Assigment assigment = assigmentRepository.findByIdAssigment(idAssigment).orElseThrow(AssigmentNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,assigment.getTeam());
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
          throw  new  UserAccessDeniedException();
        }
        List<Cost> costList=costRepository.findByAssigmentIdAssigment(idAssigment);
        List<CostResponse> costResponses=new ArrayList<>();
        for(Cost cost:costList)costResponses.add(new CostResponse(cost));
        return costResponses;
    }
    @Override
    public CostResponse saveCost(CostRequest costRequest,Integer idAssigment){
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Assigment assigment = assigmentRepository.findByIdAssigment(idAssigment).orElseThrow(AssigmentNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,assigment.getTeam());
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
            throw  new  UserAccessDeniedException();
        }
        Cost newCost = new Cost();
        newCost.setCostName(costRequest.getCostName());
        newCost.setAssigment(assigment);
        newCost.setPrice(costRequest.getPrice());
        Cost newCosted=costRepository.save(newCost);
        return new CostResponse(newCosted);
    }

    @Override
    public CostResponse getByIDCode(Integer idCode){
        Cost cost = costRepository.findByIdCost(idCode).orElseThrow(CostNotFoundException::new);
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,cost.getAssigment().getTeam());
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
            throw  new  UserAccessDeniedException();
        }
        return new CostResponse(cost);
    }
}
