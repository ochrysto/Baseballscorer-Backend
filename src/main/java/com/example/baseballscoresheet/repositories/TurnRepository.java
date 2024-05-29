package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurnRepository extends JpaRepository<TurnEntity, Long>, TruncateRepository {
    Optional<TurnEntity> findFirstByInningGameOrderByIdDesc(GameEntity game);
    Optional<TurnEntity> findByInningAndBaseAndCurrentStatus(InningEntity inning, int base, TurnEntity.Status currentStatus);
    int countByInning(InningEntity inning);
    List<TurnEntity> findByInningAndCurrentStatus(InningEntity activeInning, TurnEntity.Status status);
    List<TurnEntity> findTurnEntitiesByLineupTeamPlayer_Id(Long playerId);
}
