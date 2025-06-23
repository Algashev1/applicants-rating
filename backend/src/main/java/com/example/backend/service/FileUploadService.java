package com.example.backend.service;

import com.example.backend.model.FileUpload;
import com.example.backend.repository.FileUploadRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class FileUploadService {

    private final FileUploadRepository repository;
    private final String uploadDir = "uploads"; // Директория для файлов

    public FileUploadService(FileUploadRepository repository) {
        this.repository = repository;
    }

    public FileUpload uploadFile(MultipartFile file, String comment, LocalDate selectedDate) {
        try {
            // Сохраняем физически файл
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, originalFilename);
            Files.write(filePath, file.getBytes());

            // Создаем и сохраняем запись в БД
            FileUpload fileUpload = new FileUpload();
            fileUpload.setOriginalFilename(originalFilename);
            fileUpload.setFilePath(filePath.toString());
            fileUpload.setComment(comment);
            fileUpload.setSelectedDate(selectedDate);
            fileUpload.setUploadTimestamp(LocalDate.now());
            return repository.save(fileUpload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    public List<FileUpload> getAllFiles() {
        // Сортировка по selectedDate по убыванию
        return repository.findAll(Sort.by(Sort.Direction.DESC, "selectedDate"));
    }

    public void deleteFile(Long id) {
        FileUpload fileUpload = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        try {
            Files.deleteIfExists(Paths.get(fileUpload.getFilePath()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete physical file", e);
        }
        repository.delete(fileUpload);
    }
}