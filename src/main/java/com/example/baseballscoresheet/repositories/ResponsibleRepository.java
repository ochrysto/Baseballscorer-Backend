package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsibleRepository extends JpaRepository<ResponsibleEntity, Long> {
}
