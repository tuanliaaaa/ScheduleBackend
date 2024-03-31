package com.g11.schedule.repository;

import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Assigment;
import com.g11.schedule.entity.AssigmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AssigmentUserRepository  extends JpaRepository<AssigmentUser, Integer> {
    @Query("SELECT au FROM AssigmentUser au " +
            "JOIN au.user u " +
            "JOIN au.assigment a " +
            "WHERE u.idUser = :userId AND a.team.idTeam = :teamId")
    List<AssigmentUser> findByUserIdAndTeamId(@Param("userId") int userId, @Param("teamId") int teamId);

    @Query("SELECT au FROM AssigmentUser au " +
            "JOIN au.user u " +
            "JOIN au.assigment a " +
            "WHERE u.idUser = :userId AND a.idAssigment = :assignmentId")
    AssigmentUser findAssigmentUserByAssigmentAndUser(@Param("assignmentId") int assignmentId, @Param("userId") int userId);

}
