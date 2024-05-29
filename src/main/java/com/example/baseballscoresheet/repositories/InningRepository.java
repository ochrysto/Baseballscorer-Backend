package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InningRepository extends JpaRepository<InningEntity, Long>, TruncateRepository {
    Optional<InningEntity> findFirstByGameOrderByIdDesc(GameEntity game);
    List<InningEntity> findInningEntitiesByGame_IdAndBattingTeamOrderByIdAsc(Long game_id, InningEntity.Team battingTeam);
}
