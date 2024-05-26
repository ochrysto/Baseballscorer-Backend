package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.repositories.InningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InningService {
    private final InningRepository inningRepository;

    @Autowired
    public InningService(InningRepository inningRepository) {
        this.inningRepository = inningRepository;
    }

    public void create(InningEntity inning) {
        this.inningRepository.save(inning);
    }
}
