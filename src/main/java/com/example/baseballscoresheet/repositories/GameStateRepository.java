package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameStateEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameStateEntity, Long>, TruncateRepository {
}
