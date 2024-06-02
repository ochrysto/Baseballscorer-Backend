package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.UmpireEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UmpireRepository extends JpaRepository<UmpireEntity, Long>, TruncateRepository {
}