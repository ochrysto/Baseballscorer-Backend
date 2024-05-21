package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.ScorerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScorerRepository extends JpaRepository<ScorerEntity, Long> {
}
