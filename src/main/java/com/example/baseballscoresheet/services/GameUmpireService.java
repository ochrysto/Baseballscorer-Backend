package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.GameUmpireEntity;
import com.example.baseballscoresheet.repositories.GameUmpireRepository;
import org.springframework.stereotype.Service;

@Service
public class GameUmpireService {

    private final GameUmpireRepository gameUmpireRepository;

    public GameUmpireService(GameUmpireRepository gameUmpireRepository) {
        this.gameUmpireRepository = gameUmpireRepository;
    }


    public void save(GameUmpireEntity gameUmpire) {
        this.gameUmpireRepository.save(gameUmpire);
    }
}
