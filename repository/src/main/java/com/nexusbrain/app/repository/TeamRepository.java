package com.nexusbrain.app.repository;

import com.nexusbrain.app.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
