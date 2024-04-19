package com.g11.schedule.repository;

import com.g11.schedule.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostRepository  extends JpaRepository<Cost, Integer> {
    List<Cost> findByAssigmentIdAssigment(Integer assigmentID);
    Optional<Cost> findByIdCost(Integer codeId);

    @Query("select c from Cost c where c.assigment.idAssigment = :idAssignment")
    List<Cost> findCostByIdAssignment(Integer idAssignment);
}
