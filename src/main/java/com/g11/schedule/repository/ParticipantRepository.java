package com.g11.schedule.repository;

import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Participant;
import com.g11.schedule.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    List<Participant> findByUserAndTeam(Account user, Team team);
}
