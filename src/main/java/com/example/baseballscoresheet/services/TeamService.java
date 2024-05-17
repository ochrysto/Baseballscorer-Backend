package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.model.TeamPlayerEntity;
import com.example.baseballscoresheet.repositories.TeamPlayerRepository;
import com.example.baseballscoresheet.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, TeamPlayerRepository teamPlayerRepository) {
        this.teamRepository = teamRepository;
        this.teamPlayerRepository = teamPlayerRepository;
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

    public TeamEntity findTeamById(Long id) {
        if (this.teamRepository.findById(id).isPresent()) {
            return this.teamRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id " + id + " not found");
        }
    }

    public TeamEntity update(TeamEntity updatedTeamEntity) {
        TeamEntity updatedTeam;
        updatedTeam = this.findTeamById(updatedTeamEntity.getId());
        updatedTeam.setName(updatedTeamEntity.getName());
        updatedTeam.setManager(updatedTeamEntity.getManager());
        updatedTeam.setClub(updatedTeamEntity.getClub());
        updatedTeam.setLeague(updatedTeamEntity.getLeague());
        updatedTeam.setTeamLogo(updatedTeamEntity.getTeamLogo());
        if (updatedTeamEntity.getTeamplayer() != null) {
            for (TeamPlayerEntity teamPlayerEntity : updatedTeamEntity.getTeamplayer()) {
                updatedTeam.getTeamplayer().add(teamPlayerEntity);
            }
        }
        this.teamRepository.save(updatedTeam);
        return updatedTeam;
    }

    public void delete(Long id) {
        this.teamRepository.deleteById(id);
    }

    public void deletePlayerFromTeam(Long teamId, Long playerId) {
        teamPlayerRepository.deleteByPlayer_IdAndTeam_Id(playerId, teamId);
    }

    public List<PlayerEntity> getAllPlayersFromTeam(Long teamId) {
        List<PlayerEntity> players = new ArrayList<>();

        TeamEntity team = findTeamById(teamId);
        if (!team.getTeamplayer().isEmpty()) {
            for (TeamPlayerEntity teamPlayer : team.getTeamplayer()) {
                players.add(teamPlayer.getPlayer());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id " + teamId + " has no players.");
        }
        return players;
    }
}