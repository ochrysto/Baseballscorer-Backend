package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity, Long> {

    Optional<GameEntity> findByGameNr(Integer gameNr);
}
