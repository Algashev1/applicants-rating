package com.example.backend.repository;

import com.example.backend.model.Abiturient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AbiturientRepository extends JpaRepository<Abiturient, Long> {
    Optional<Abiturient> findByPersonalNumber(String personalNumber);
}