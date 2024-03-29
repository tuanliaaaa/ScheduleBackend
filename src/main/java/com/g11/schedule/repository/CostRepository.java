package com.g11.schedule.repository;

import com.g11.schedule.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostRepository  extends JpaRepository<Cost, Integer> {
    List<Cost> findByTeamIdTeam(Integer teamId);
    Optional<Cost> findByIdCost(Integer codeId);
}
