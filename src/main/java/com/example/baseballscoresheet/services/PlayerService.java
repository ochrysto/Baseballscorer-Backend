package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<PlayerEntity> findPlayerById(Long id) {
        return this.playerRepository.findById(id);
    }

    public Optional<PlayerEntity> getPlayerById(Long playerId) {
        return this.playerRepository.findById(playerId);
    }
}
