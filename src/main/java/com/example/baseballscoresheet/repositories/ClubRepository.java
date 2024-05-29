package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.ClubEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<ClubEntity, Long>, TruncateRepository {
}
