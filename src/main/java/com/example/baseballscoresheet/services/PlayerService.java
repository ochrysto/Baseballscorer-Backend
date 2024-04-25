package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerEntity createPlayer(PlayerEntity newPlayer) {
        return this.playerRepository.save(newPlayer);
    }

    public Optional<PlayerEntity> findPlayerById(Long playerId) {
        return this.playerRepository.findById(playerId);
    }

    public List<PlayerEntity> findAllPlayers() {
        return this.playerRepository.findAll();
    }

    public PlayerEntity update(PlayerEntity updatedPlayerEntity) {
        PlayerEntity updatedPlayer;
        if (this.playerRepository.findById(updatedPlayerEntity.getId()).isEmpty()) {
            throw new RessourceNotFoundException("Player with the id = " + updatedPlayerEntity.getId() + " not found");
        } else {
            updatedPlayer = this.findPlayerById(updatedPlayerEntity.getId()).get();
            updatedPlayer.setFirstName(updatedPlayerEntity.getFirstName());
            updatedPlayer.setLastName(updatedPlayerEntity.getLastName());
            updatedPlayer.setPassnumber(updatedPlayer.getPassnumber());
            this.playerRepository.save(updatedPlayer);
            return updatedPlayer;
        }
    }
}