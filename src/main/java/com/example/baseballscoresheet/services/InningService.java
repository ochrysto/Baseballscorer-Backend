package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.repositories.InningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InningService {
    private final InningRepository inningRepository;

    @Autowired
    public InningService(InningRepository inningRepository) {
        this.inningRepository = inningRepository;
    }

    public InningEntity create(InningEntity inning) {
        return this.inningRepository.save(inning);
    }

    public List<InningEntity> getByGameAndTeam(long gameId, InningEntity.Team team) {
        return inningRepository.findInningEntitiesByGame_IdAndBattingTeamOrderByIdAsc(gameId, team);
    }
}
