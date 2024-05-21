package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.entities.ManagerEntity;
import com.example.baseballscoresheet.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ManagerEntity getManagerById(Long id) {
        if (this.managerRepository.findById(id).isPresent()) {
            return this.managerRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager with id " + id + " not found");
        }
    }

    public List<ManagerEntity> readAll() {
        return managerRepository.findAll();
    }
}
