package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.model.entities.PlayerEntity;
import com.example.baseballscoresheet.repositories.PlayerRepository;
import com.example.baseballscoresheet.repositories.TeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    PlayerRepository playerRepository;
    TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamPlayerRepository teamPlayerRepository) {
        this.playerRepository = playerRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public PlayerEntity createPlayer(PlayerEntity newPlayer) {
        return this.playerRepository.save(newPlayer);
    }

    public PlayerEntity findPlayerById(Long playerId) {
        if (this.playerRepository.findById(playerId).isPresent()) {
            return this.playerRepository.findById(playerId).get();
        } else {
            throw new RessourceNotFoundException("Player with id: " + playerId + " not found");
        }
    }

    public List<PlayerEntity> findAllPlayers() {
        return this.playerRepository.findAll();
    }

    public PlayerEntity update(PlayerEntity updatedPlayerEntity) {
        PlayerEntity updatedPlayer;
        updatedPlayer = this.findPlayerById(updatedPlayerEntity.getId());
        updatedPlayer.setFirstName(updatedPlayerEntity.getFirstName());
        updatedPlayer.setLastName(updatedPlayerEntity.getLastName());
        updatedPlayer.setPassnumber(updatedPlayerEntity.getPassnumber());
        this.playerRepository.save(updatedPlayer);
        return updatedPlayer;

    }

    public void delete(Long id) {
        this.playerRepository.deleteById(id);
    }

    public boolean isPlayerAssignedToATeam(Long playerId) {
        return this.teamPlayerRepository.existsByPlayer_Id(playerId);
    }
}