package com.nexusbrain.app.repository;

import com.nexusbrain.app.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface WorkerRepository extends PagingAndSortingRepository<Worker, Long> {

    @Query("SELECT w FROM Worker w WHERE " +
            "(:phrase IS NULL OR w.email LIKE %:phrase% OR w.fullName LIKE %:phrase%) AND " +
            "(:teamId IS NULL OR w.team.id = :teamId)")
    Page<Worker> findWorkers(Pageable pageable, @Param("phrase") String phrase, @Param("teamId") Long teamId);

}
