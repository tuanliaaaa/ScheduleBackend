package com.g11.schedule.repository;

import com.g11.schedule.entity.AssigmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AssigmentUserRepository  extends JpaRepository<AssigmentUser, Integer> {
}
