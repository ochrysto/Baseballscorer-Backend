package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TurnRepository extends JpaRepository<TurnEntity, Long> {
    Optional<TurnEntity> findFirstByInningGameOrderByIdDesc(GameEntity game);
    Optional<TurnEntity> findByInningAndBaseAndCurrentStatus(InningEntity inning, int base, String currentStatus);

    int countByInning(InningEntity inning);

    List<TurnEntity> findByInningAndCurrentStatus(InningEntity activeInning, String status);
}
