package com.g11.schedule.service.impl;

import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Participant;
import com.g11.schedule.entity.Team;
import com.g11.schedule.repository.ParticipantRepository;
import com.g11.schedule.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;


}
