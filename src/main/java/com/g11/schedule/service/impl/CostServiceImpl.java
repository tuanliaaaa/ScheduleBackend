package com.g11.schedule.service.impl;

import com.g11.schedule.dto.request.CostRequest;
import com.g11.schedule.dto.response.CostResponse;
import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Cost;
import com.g11.schedule.entity.Participant;
import com.g11.schedule.entity.Team;
import com.g11.schedule.exception.Cost.CostNotFoundException;
import com.g11.schedule.exception.Team.TeamNotFoundException;
import com.g11.schedule.exception.User.UserAccessDeniedException;
import com.g11.schedule.exception.User.UsernameNotFoundException;
import com.g11.schedule.repository.AccountRepository;
import com.g11.schedule.repository.CostRepository;
import com.g11.schedule.repository.ParticipantRepository;
import com.g11.schedule.repository.TeamRepository;
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
    private final TeamRepository teamRepository;
    private final AccountRepository accountRepository;
    private final ParticipantRepository participantRepository;
    @Override
    public List<CostResponse> findCostByIDTeam(Integer idTeam){
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Team team = teamRepository.findByIdTeam(idTeam).orElseThrow(TeamNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,team);
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
          throw  new  UserAccessDeniedException();
        }
        List<Cost> costList=costRepository.findByTeamIdTeam(idTeam);
        List<CostResponse> costResponses=new ArrayList<>();
        for(Cost cost:costList)costResponses.add(new CostResponse(cost));
        return costResponses;
    }
    @Override
    public CostResponse saveCost(CostRequest costRequest){
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        Team team = teamRepository.findByIdTeam(costRequest.getIDTeam()).orElseThrow(TeamNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,team);
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
            throw  new  UserAccessDeniedException();
        }
        Cost newCost = new Cost();
        newCost.setCostName(costRequest.getCostName());
        newCost.setTeam(team);
        newCost.setPrice(costRequest.getPrice());
        Cost newCosted=costRepository.save(newCost);
        return new CostResponse(newCosted);
    }

    @Override
    public CostResponse getByIDCode(Integer idCode){
        Cost cost = costRepository.findByIdCost(idCode).orElseThrow(CostNotFoundException::new);
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account= accountRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        List<Participant> participantList =participantRepository.findByUserAndTeam(account,cost.getTeam());
        if((participantList.size()!=0&&!participantList.get(0).getPosition().equals("manage"))||participantList.size()==0){
            throw  new  UserAccessDeniedException();
        }
        return new CostResponse(cost);
    }
}
