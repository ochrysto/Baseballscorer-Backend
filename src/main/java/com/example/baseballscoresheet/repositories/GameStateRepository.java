package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.GameStateEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameStateRepository extends JpaRepository<GameStateEntity, Long>, TruncateRepository {
    Optional<GameStateEntity> findByGame(GameEntity game);
}
