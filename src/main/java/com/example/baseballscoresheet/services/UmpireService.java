package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.UmpireEntity;
import com.example.baseballscoresheet.repositories.UmpireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmpireService {

    private final UmpireRepository umpireRepository;

    @Autowired
    public UmpireService(UmpireRepository umpireRepository) {
        this.umpireRepository = umpireRepository;
    }

    public List<UmpireEntity> readAll() {
        return this.umpireRepository.findAll();
    }
}