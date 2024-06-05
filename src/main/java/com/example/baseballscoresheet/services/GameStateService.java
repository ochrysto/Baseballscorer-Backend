package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.InternalServerError;
import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.GameStateEntity;
import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.repositories.GameStateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameStateService {
    private final GameStateRepository gameStateRepository;

    public GameStateService(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    public GameStateEntity create(GameStateEntity gameStateEntity) {
        return this.gameStateRepository.save(gameStateEntity);
    }

    public void increaseHitPoints(GameEntity game, InningEntity.Team team) {
        Optional<GameStateEntity> gameState = this.gameStateRepository.findByGame(game);
        if (gameState.isPresent()) {
            GameStateEntity gameStateEntity = gameState.get();
            switch (team) {
                case HOME -> gameStateEntity.setHomeHits(gameStateEntity.getHomeHits() + 1);
                case AWAY -> gameStateEntity.setAwayHits(gameStateEntity.getAwayHits() + 1);
            }
            this.gameStateRepository.save(gameStateEntity);
        } else {
            throw new InternalServerError("Cannot find GameStateEntity for game with id " + game.getId());
        }
    }
}
