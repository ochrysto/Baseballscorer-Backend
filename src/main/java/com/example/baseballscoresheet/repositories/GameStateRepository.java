package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameStateEntity, Long> {
}
