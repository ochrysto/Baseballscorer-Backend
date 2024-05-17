package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.LeagueEntity;
import com.example.baseballscoresheet.repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "League wih id " + leagueId + " not found.");
        }
    }

    public List<LeagueEntity> readAll() {
        return leagueRepository.findAll();
    }
}
