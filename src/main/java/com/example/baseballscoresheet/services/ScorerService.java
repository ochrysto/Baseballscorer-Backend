package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.ScorerEntity;
import com.example.baseballscoresheet.repositories.ScorerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScorerService {

    private final ScorerRepository scorerRepository;

    @Autowired
    public ScorerService(ScorerRepository scorerRepository) {
        this.scorerRepository = scorerRepository;
    }

    public List<ScorerEntity> readAll() {
        return this.scorerRepository.findAll();
    }
}