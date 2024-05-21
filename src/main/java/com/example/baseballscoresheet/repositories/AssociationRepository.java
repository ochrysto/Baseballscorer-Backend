package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.AssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<AssociationEntity, Long> {
}