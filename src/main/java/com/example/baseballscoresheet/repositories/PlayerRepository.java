package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
}
