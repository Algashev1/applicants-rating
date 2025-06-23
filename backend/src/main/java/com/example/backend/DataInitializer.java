package com.example.backend;

import com.example.backend.model.Institute;
import com.example.backend.repository.InstituteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(InstituteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // repository.save(new Institute("Институт математики"));
                // repository.save(new Institute("Институт физики"));
            }
        };
    }
}
