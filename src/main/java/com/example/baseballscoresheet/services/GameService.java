package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameEntity createGame(GameEntity gameEntity) {
        return this.gameRepository.save(gameEntity);
    }
}
