package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.TeamEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long>, TruncateRepository {
}
