package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.PlayerEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>, TruncateRepository {
}
