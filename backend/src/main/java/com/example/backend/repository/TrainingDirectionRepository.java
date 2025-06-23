package com.example.backend.repository;

import com.example.backend.model.TrainingDirection;
import com.example.backend.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingDirectionRepository extends JpaRepository<TrainingDirection, Long> {
    TrainingDirection findByNameAndInstitute(String name, Institute institute);
    List<TrainingDirection> findByInstitute(Institute institute);
}