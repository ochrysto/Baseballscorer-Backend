package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.PositionEntity;
import com.example.baseballscoresheet.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<PositionEntity> findAll() {
        return this.positionRepository.findAll();
    }
}
