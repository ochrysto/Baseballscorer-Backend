package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import com.example.baseballscoresheet.repositories.ResponsibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsibleService {
    @Autowired
    private ResponsibleRepository responsibleRepository;

    public ResponsibleEntity save(ResponsibleEntity responsibleEntity) {
        return responsibleRepository.save(responsibleEntity);
    }
}
