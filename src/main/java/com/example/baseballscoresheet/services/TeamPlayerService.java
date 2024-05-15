package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.TeamPlayerEntity;
import com.example.baseballscoresheet.repositories.TeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerService {
    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamPlayerService(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public void createTeamPlayer(TeamPlayerEntity teamPlayerEntity) {
        this.teamPlayerRepository.save(teamPlayerEntity);
    }
}