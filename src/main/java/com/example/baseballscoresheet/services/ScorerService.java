package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.model.entities.ScorerEntity;
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

    public ScorerEntity findById(Long scorerId) {
        if (this.scorerRepository.findById(scorerId).isPresent()) {
            return this.scorerRepository.findById(scorerId).get();
        } else {
            throw new RessourceNotFoundException("Scorer with id: " + scorerId + " not found");
        }
    }
}