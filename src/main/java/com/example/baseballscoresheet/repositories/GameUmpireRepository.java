package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameUmpireEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameUmpireRepository extends JpaRepository<GameUmpireEntity, Long> {

    List<GameUmpireEntity> findAllByGame_Id(Long gameId);
}
