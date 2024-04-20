package com.example.baseballscoresheet.services;

import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Optional<ManagerEntity> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    public List<ManagerEntity> readAll() {
        return managerRepository.findAll();
    }
}
