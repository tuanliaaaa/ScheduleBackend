package com.g11.schedule.repository;

import com.g11.schedule.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository  extends JpaRepository<Cost, Integer> {

}
