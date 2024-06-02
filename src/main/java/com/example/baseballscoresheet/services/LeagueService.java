package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.exceptionHandling.ResourceNotFoundException;
import com.example.baseballscoresheet.model.entities.LeagueEntity;
import com.example.baseballscoresheet.repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public LeagueEntity getLeagueById(Long leagueId) {
        if (leagueRepository.findById(leagueId).isPresent()) {
            return leagueRepository.findById(leagueId).get();
        } else {
            throw new ResourceNotFoundException("League with id: " + leagueId + " not found");
        }
    }

    public List<LeagueEntity> readAll() {
        return leagueRepository.findAll();
    }

    public LeagueEntity create(LeagueEntity leagueEntity) {
        return leagueRepository.save(leagueEntity);
    }
}
