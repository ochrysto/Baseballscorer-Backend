package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.repositories.PlayerRepository;
import com.example.baseballscoresheet.repositories.TeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    public Optional<PlayerEntity> findPlayerById(Long playerId) {
        return this.playerRepository.findById(playerId);
    }

    public List<PlayerEntity> findAllPlayers() {
        return this.playerRepository.findAll();
    }

    public PlayerEntity update(PlayerEntity updatedPlayerEntity) {
        PlayerEntity updatedPlayer;
        if (this.playerRepository.findById(updatedPlayerEntity.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player with the id = " + updatedPlayerEntity.getId() + " not found");
        } else {
            updatedPlayer = this.findPlayerById(updatedPlayerEntity.getId()).get();
            updatedPlayer.setFirstName(updatedPlayerEntity.getFirstName());
            updatedPlayer.setLastName(updatedPlayerEntity.getLastName());
            updatedPlayer.setPassnumber(updatedPlayerEntity.getPassnumber());
            this.playerRepository.save(updatedPlayer);
            return updatedPlayer;
        }
    }

    public void delete(Long id) {
        this.playerRepository.deleteById(id);
    }

    public boolean isPlayerAssignedToATeam(Long playerId) {
        return this.teamPlayerRepository.existsByPlayer_Id(playerId);
    }
}