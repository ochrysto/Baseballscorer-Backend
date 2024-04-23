package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamEntity createTeam(TeamEntity teamEntity) {
        return this.teamRepository.save(teamEntity);
    }

    public List<TeamEntity> findAll() {
        return this.teamRepository.findAll();
    }

    public List<ManagerEntity> findAllManagersInTeams() {
        List<TeamEntity> allTeams = this.findAll();
        List<ManagerEntity> allManagersInTeams = new ArrayList<>();

        for (TeamEntity teamEntity : allTeams) {
            allManagersInTeams.add(teamEntity.getManager());
        }
        return allManagersInTeams;
    }
}