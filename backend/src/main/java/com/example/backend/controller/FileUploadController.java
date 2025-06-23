package com.example.backend.controller;

import com.example.backend.model.FileUpload;
import com.example.backend.repository.FileUploadRepository;
import com.example.backend.service.FileUploadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.backend.service.AbiturientService;
import com.example.backend.service.StatementService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileUploadRepository repository;
    private final FileUploadService service;
    private final AbiturientService abiturientService;
    private final StatementService statementService;


    public FileUploadController(FileUploadRepository repository, FileUploadService service, AbiturientService abiturientService, StatementService statementService) {
        this.repository = repository;
        this.service = service;
        this.abiturientService = abiturientService;
        this.statementService = statementService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUpload> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("comment") String comment,
            @RequestParam("selectedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
                abiturientService.importAbiturients(file);
                statementService.importStatements(file, selectedDate);
        return ResponseEntity.ok(service.uploadFile(file, comment, selectedDate));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        FileUpload fileUpload = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        try {
            Path path = Paths.get(fileUpload.getFilePath());
            Resource resource = new UrlResource(path.toUri());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileUpload.getOriginalFilename() + "\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        service.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FileUpload>> getAllFiles() {
        // Получаем файлы, отсортированные от самой новой выбранной даты до старой
        return ResponseEntity.ok(service.getAllFiles());
    }
}