package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.UmpireEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UmpireRepository extends JpaRepository<UmpireEntity, Long> {
}