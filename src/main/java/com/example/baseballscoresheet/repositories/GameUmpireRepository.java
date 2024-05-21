package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameUmpireEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameUmpireRepository extends JpaRepository<GameUmpireEntity, Long> {
}
