package com.example.baseballscoresheet;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Utility {

    private static PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        Utility.playerService = playerService;
    }

    public static PlayerEntity returnPlayerIfExists(Long playerId) {
        PlayerEntity playerEntity;
        Optional<PlayerEntity> playerOptional = playerService.findPlayerById(playerId);
        if (playerOptional.isPresent()) {
            // adds ManagerEntity to TeamEntity if a DB entry is found
            playerEntity = playerOptional.get();
        } else {
            // throws exception if no suitable manager was found in DB
            throw new RessourceNotFoundException("Player with id " + playerId + " was not found.");
        }
        return playerEntity;
    }
}
