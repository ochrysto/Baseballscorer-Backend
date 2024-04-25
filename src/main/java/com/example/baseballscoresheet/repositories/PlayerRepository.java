package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
}
