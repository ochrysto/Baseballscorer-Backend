package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.AssociationEntity;
import com.example.baseballscoresheet.repositories.AssociationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AssociationService {

    private final AssociationRepository associationRepository;

    public AssociationService(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    public List<AssociationEntity> readAll() {
        return this.associationRepository.findAll();
    }

    public AssociationEntity findById(Long associationId) {
        if (this.associationRepository.findById(associationId).isPresent()) {
            return this.associationRepository.findById(associationId).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Association with id " + associationId + " not found");
        }
    }
}