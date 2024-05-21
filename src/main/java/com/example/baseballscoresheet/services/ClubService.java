package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.ClubEntity;
import com.example.baseballscoresheet.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public ClubEntity getClubById(Long clubId) {
        if (clubRepository.findById(clubId).isPresent()) {
            return clubRepository.findById(clubId).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club with id " + clubId + " not found");
        }
    }

    public List<ClubEntity> readAll() {
        return clubRepository.findAll();
    }
}
