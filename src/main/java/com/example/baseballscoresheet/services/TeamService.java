package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.RessourceNotFoundException;
import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<TeamEntity> findTeamById(Long id) {
        return this.teamRepository.findById(id);
    }

    public TeamEntity update(TeamEntity updatedTeamEntity) {
        TeamEntity updatedTeam;
        if (this.teamRepository.findById(updatedTeamEntity.getId()).isEmpty()) {
            throw new RessourceNotFoundException("Team with the id = " + updatedTeamEntity.getId() + " not found");
        } else {
            updatedTeam = this.findTeamById(updatedTeamEntity.getId()).get();
            updatedTeam.setName(updatedTeamEntity.getName());
            updatedTeam.setManager(updatedTeamEntity.getManager());
            updatedTeam.setClub(updatedTeamEntity.getClub());
            updatedTeam.setLeague(updatedTeamEntity.getLeague());
            updatedTeam.setTeamLogo(updatedTeamEntity.getTeamLogo());
            this.teamRepository.save(updatedTeam);
            return updatedTeam;
        }
    }

    public void delete(Long id) {
        this.teamRepository.deleteById(id);
    }
}