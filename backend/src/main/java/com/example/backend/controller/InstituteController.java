package com.example.backend.controller;

import com.example.backend.dto.TrainingDirectionInfoDTO;
import com.example.backend.model.Institute;
import com.example.backend.repository.InstituteRepository;
import com.example.backend.service.InstituteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institutes")
public class InstituteController {

    private final InstituteRepository instituteRepository;
    private final InstituteService instituteService;

    public InstituteController(InstituteRepository instituteRepository, InstituteService instituteService) {
        this.instituteRepository = instituteRepository;
        this.instituteService = instituteService;
    }

    // Получить список всех институтов
    @GetMapping
    public List<Institute> getAllInstitutes() {
        return instituteRepository.findAll();
    }

    // Получить направления с количеством заявлений и приоритетом 1 для выбранного института
    @GetMapping("/{id}/directions-with-stats")
    public DirectionsWithStatsResponse getDirectionsWithStats(@PathVariable Long id) {
        List<TrainingDirectionInfoDTO> directions = instituteService.getDirectionsWithStats(id);
        String instituteName = instituteRepository.findById(id)
                .map(Institute::getName)
                .orElse("");
        return new DirectionsWithStatsResponse(instituteName, directions);
    }

    // DTO для ответа
    public static class DirectionsWithStatsResponse {
        private String instituteName;
        private List<TrainingDirectionInfoDTO> directions;

        public DirectionsWithStatsResponse(String instituteName, List<TrainingDirectionInfoDTO> directions) {
            this.instituteName = instituteName;
            this.directions = directions;
        }

        public String getInstituteName() {
            return instituteName;
        }

        public void setInstituteName(String instituteName) {
            this.instituteName = instituteName;
        }

        public List<TrainingDirectionInfoDTO> getDirections() {
            return directions;
        }

        public void setDirections(List<TrainingDirectionInfoDTO> directions) {
            this.directions = directions;
        }
    }
}
