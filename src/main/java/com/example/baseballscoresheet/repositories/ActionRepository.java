package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.TurnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
    ActionEntity findFirstByTurnOrderByIdDesc(TurnEntity turn);

    ActionEntity findByLinkedAction(ActionEntity lastAction);
}
