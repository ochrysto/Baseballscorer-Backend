package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.LineupEntity;
import com.example.baseballscoresheet.repositories.LineupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineupService {

    private final LineupRepository lineupRepository;

    @Autowired
    public LineupService(com.example.baseballscoresheet.repositories.LineupRepository lineupRepository) {
        this.lineupRepository = lineupRepository;
    }

    public LineupEntity saveLineup(LineupEntity lineupEntity) {
        return this.lineupRepository.save(lineupEntity);
    }
}
