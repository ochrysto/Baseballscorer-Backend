package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
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

    public GameEntity findGameByGameNr(Integer gameNr) {
        if (this.gameRepository.findByGameNr(gameNr).isPresent()) {
            return this.gameRepository.findByGameNr(gameNr).get();
        }
        throw new RessourceNotFoundException("Game with game number: " + gameNr + " not found");
    }

    private GameEntity findGameById(Long id) {
        if (this.gameRepository.findById(id).isPresent()) {
            return this.gameRepository.findById(id).get();
        } else {
            throw new RessourceNotFoundException("Game with id: " + id + " not found");
        }
    }

    public GameEntity finish(GameEntity updatedGameEntity) {
        GameEntity gameEntity = this.findGameById(updatedGameEntity.getId());
        gameEntity.setInnings(updatedGameEntity.getInnings());
        gameEntity.setAttendance(updatedGameEntity.getAttendance());
        gameEntity.setEndTime(updatedGameEntity.getEndTime());
        gameEntity.setStartTime(updatedGameEntity.getStartTime());
        gameEntity.setDurationInMinutes(updatedGameEntity.getDurationInMinutes());
        this.gameRepository.save(gameEntity);
        return gameEntity;
    }
}
