package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.repositories.TeamRepository;

public class TeamService {

    private TeamRepository teamRepository;

    public TeamEntity createTeam(TeamEntity teamEntity) {
        return this.teamRepository.save(teamEntity);
    }
}
