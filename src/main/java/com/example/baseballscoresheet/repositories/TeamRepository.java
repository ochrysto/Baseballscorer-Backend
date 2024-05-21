package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
