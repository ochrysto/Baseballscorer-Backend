package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.repositories.InningRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    public InningEntity createNext(InningEntity old) {
        InningEntity inning = new InningEntity();
        inning.setGame(old.getGame());
        inning.setOuts(0);

        switch (old.getBattingTeam()) {
            case AWAY -> {
                inning.setBattingTeam(InningEntity.Team.HOME);
                inning.setInning(old.getInning());  // continue this inning
            }
            case HOME -> {
                inning.setBattingTeam(InningEntity.Team.AWAY);
                inning.setInning(old.getInning() + 1);  // next inning
            }
        }

        return inningRepository.save(inning);
    }

    public InningEntity createFirstInning(GameEntity game) {
        InningEntity inning = new InningEntity();
        inning.setGame(game);
        inning.setInning(1);  // we always start a game with inning = 1
        inning.setOuts(0);
        inning.setBattingTeam(InningEntity.Team.AWAY);
        return inningRepository.save(inning);
    }

    public InningEntity increaseOuts(InningEntity inning) {
        inning.setOuts(inning.getOuts() + 1);
        return inningRepository.save(inning);
    }
}
