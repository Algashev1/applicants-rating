package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "file_uploads")
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;
    private String filePath;
    private String comment;

    // Системная отметка времени загрузки файла (если нужна)
    private LocalDate uploadTimestamp;

    // Новое поле – выбранная дата (обязательное)
    @Column(name = "selected_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate selectedDate;

    // Getters и Setters

    public Long getId() {
        return id;
    }
    public String getOriginalFilename() {
        return originalFilename;
    }
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDate getUploadTimestamp() {
        return uploadTimestamp;
    }
    public void setUploadTimestamp(LocalDate uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }
    public LocalDate getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }
}