package com.g11.schedule.repository;

import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssigmentRepository  extends JpaRepository<Assigment, Integer> {
    Optional<Assigment> findByIdAssigment(Integer idAssigment);
}
