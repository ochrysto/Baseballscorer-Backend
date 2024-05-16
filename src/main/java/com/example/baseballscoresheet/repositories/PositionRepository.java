package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {
}
