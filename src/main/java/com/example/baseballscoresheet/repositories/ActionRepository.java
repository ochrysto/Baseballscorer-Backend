package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long>, TruncateRepository {
    ActionEntity findFirstByTurnOrderByIdDesc(TurnEntity turn);

    ActionEntity findByLinkedAction(ActionEntity lastAction);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE action", nativeQuery = true)
    void truncateTable();
}
