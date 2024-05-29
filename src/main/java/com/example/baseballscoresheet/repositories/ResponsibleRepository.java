package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsibleRepository extends JpaRepository<ResponsibleEntity, Long>, TruncateRepository {
}
