package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.GameStateEntity;
import com.example.baseballscoresheet.repositories.GameStateRepository;
import org.springframework.stereotype.Service;

@Service
public class GameStateService {
    private final GameStateRepository gameStateRepository;

    public GameStateService(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    public GameStateEntity create(GameStateEntity gameStateEntity) {
        return gameStateRepository.save(gameStateEntity);
    }
}
