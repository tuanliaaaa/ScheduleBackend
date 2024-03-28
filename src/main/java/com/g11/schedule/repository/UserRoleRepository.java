package com.g11.schedule.repository;

import com.g11.schedule.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository  extends JpaRepository<UserRole, Integer> {
}
