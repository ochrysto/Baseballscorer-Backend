package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.entities.ManagerEntity;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<ManagerEntity, Long>, TruncateRepository {
}
