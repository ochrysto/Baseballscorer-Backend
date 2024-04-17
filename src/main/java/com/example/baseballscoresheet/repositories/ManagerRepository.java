package com.example.baseballscoresheet.repositories;

import com.example.baseballscoresheet.model.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
}
