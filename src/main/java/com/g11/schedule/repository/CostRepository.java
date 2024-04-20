package com.g11.schedule.repository;

import com.g11.schedule.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CostRepository  extends JpaRepository<Cost, Integer> {
    List<Cost> findByAssigmentIdAssigment(Integer assigmentID);
    Optional<Cost> findByIdCost(Integer codeId);

    @Query("select c from Cost c where c.assigment.idAssigment = :idAssignment")
    List<Cost> findCostByIdAssignment(Integer idAssignment);

    @Query("SELECT c FROM Cost c WHERE c.assigment.team.idTeam = :idTeam " +
            "AND c.refundDay BETWEEN :fromDate AND :toDate " +
            "ORDER BY c.refundDay DESC")
    List<Cost> findCostsByRefundDateBetweenInTeam(@Param("idTeam") Integer idTeam, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
