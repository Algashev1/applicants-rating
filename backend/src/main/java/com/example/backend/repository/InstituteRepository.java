package com.example.backend.repository;

import com.example.backend.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Institute findByName(String name);
}
