package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.AssociationEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<AssociationEntity, Long>, TruncateRepository {
}