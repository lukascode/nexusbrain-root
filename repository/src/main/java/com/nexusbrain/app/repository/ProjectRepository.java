package com.nexusbrain.app.repository;

import com.nexusbrain.app.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.teams WHERE :phrase IS NULL OR p.name LIKE %:phrase% OR p.description LIKE %:phrase%")
    List<Project> findProjects(@Param("phrase") String phrase);
}
