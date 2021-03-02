package com.nexusbrain.app.repository;

import com.nexusbrain.app.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.workers LEFT JOIN FETCH t.project WHERE :phrase IS NULL OR t.name LIKE %:phrase% OR t.description LIKE %:phrase%")
    List<Team> findTeams(@Param("phrase") String phrase);

}
