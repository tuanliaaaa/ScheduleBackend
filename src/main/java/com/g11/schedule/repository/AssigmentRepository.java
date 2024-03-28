package com.g11.schedule.repository;

import com.g11.schedule.entity.Assigment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigmentRepository  extends JpaRepository<Assigment, Integer> {
}
