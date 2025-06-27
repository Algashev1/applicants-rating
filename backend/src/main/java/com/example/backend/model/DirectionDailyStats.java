package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;

@Entity
public class DirectionDailyStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instituteName;
    private String directionName;
    private LocalDate importDate;
    private int newStatements;
    private int withdrawnStatements;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInstituteName() { return instituteName; }
    public void setInstituteName(String instituteName) { this.instituteName = instituteName; }

    public String getDirectionName() { return directionName; }
    public void setDirectionName(String directionName) { this.directionName = directionName; }

    public LocalDate getImportDate() { return importDate; }
    public void setImportDate(String importDateString) { this.importDate = LocalDate.parse(importDateString); }

    public int getNewStatements() { return newStatements; }
    public void setNewStatements(int newStatements) { this.newStatements = newStatements; }

    public int getWithdrawnStatements() { return withdrawnStatements; }
    public void setWithdrawnStatements(int withdrawnStatements) { this.withdrawnStatements = withdrawnStatements; }
}
