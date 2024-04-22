package com.g11.schedule.repository;

import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository  extends JpaRepository<Team, Integer> {
    Optional<Team> findByIdTeam(Integer idTeam);

    @Query("select a from Account a " +
            "join Participant p on a.idUser = p.user.idUser " +
            "where p.team.idTeam = :idTeam")
    List<Account> getAllMemberInTeam(@Param("idTeam") Integer idTeam);

    @Query("select t from Team t " +
            "join Participant p on t.idTeam = p.team.idTeam " +
            "where p.user.idUser = :idUser " +
            "ORDER BY t.idTeam DESC")
    List<Team> getTeamByUser(@Param("idUser") Integer idUser);
}
