package com.example.backend.controller;

import com.example.backend.model.Institute;
import com.example.backend.repository.InstituteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/institutes")
@CrossOrigin(origins = "*")
public class InstituteController {
    private final InstituteRepository repository;

    public InstituteController(InstituteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Institute> getAllInstitutes() {
        return repository.findAll();
    }
}
