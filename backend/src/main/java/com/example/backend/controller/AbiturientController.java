package com.example.backend.controller;

import com.example.backend.model.Abiturient;
import com.example.backend.repository.AbiturientRepository;
import com.example.backend.service.AbiturientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/abiturients")
public class AbiturientController {

    private final AbiturientRepository repository;
    private final AbiturientService service;

    public AbiturientController(AbiturientRepository repository, AbiturientService service) {
        this.repository = repository;
        this.service = service;
    }

    // Эндпоинт для получения всех абитуриентов
    @GetMapping
    public ResponseEntity<List<Abiturient>> getAllAbiturients() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Эндпоинт для загрузки Excel-файла и импорта данных
    @PostMapping("/upload")
    public ResponseEntity<String> uploadAbiturients(@RequestParam("file") MultipartFile file) {
        service.importAbiturients(file);
        return ResponseEntity.ok("Import completed");
    }
}