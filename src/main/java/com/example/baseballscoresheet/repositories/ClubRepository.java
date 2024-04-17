package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {
}
