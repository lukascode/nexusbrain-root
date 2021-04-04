package com.nexusbrain.app.repository;

import com.nexusbrain.app.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    String FIND_WORKERS_WHERE_QUERY = "WHERE :phrase IS NULL OR w.email LIKE %:phrase% OR w.fullName LIKE %:phrase%";

    @Query(value = "SELECT w FROM Worker w LEFT JOIN FETCH w.teams t " + FIND_WORKERS_WHERE_QUERY,
            countQuery = "SELECT count(w) FROM Worker w " + FIND_WORKERS_WHERE_QUERY)
    Page<Worker> findWorkers(Pageable pageable, @Param("phrase") String phrase);
}
