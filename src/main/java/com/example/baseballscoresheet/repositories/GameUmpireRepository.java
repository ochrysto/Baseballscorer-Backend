package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameUmpireEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameUmpireRepository extends JpaRepository<GameUmpireEntity, Long>, TruncateRepository {

    List<GameUmpireEntity> findAllByGame_Id(Long gameId);
}
