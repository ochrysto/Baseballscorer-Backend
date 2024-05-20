package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.AssociationEntity;
import com.example.baseballscoresheet.repositories.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociationService {

    private final AssociationRepository associationRepository;

    @Autowired
    public AssociationService(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    public List<AssociationEntity> readAll() {
        return this.associationRepository.findAll();
    }
}