package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.ClubEntity;
import com.example.baseballscoresheet.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Optional<ClubEntity> getClubById(Long id) {
        return clubRepository.findById(id);
    }

    public List<ClubEntity> readAll() {
        return clubRepository.findAll();
    }
}
