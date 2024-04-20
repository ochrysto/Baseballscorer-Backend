package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamEntity createTeam(TeamEntity teamEntity) {
        return this.teamRepository.save(teamEntity);
    }
}
