package com.example.backend.service;

import com.example.backend.dto.TrainingDirectionInfoDTO;
import com.example.backend.model.Institute;
import com.example.backend.model.TrainingDirection;
import com.example.backend.repository.InstituteRepository;
import com.example.backend.repository.TrainingDirectionRepository;
import com.example.backend.repository.StatementRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class InstituteService {
    private final InstituteRepository instituteRepository;
    private final TrainingDirectionRepository trainingDirectionRepository;
    private final StatementRepository statementRepository;

    public InstituteService(
        InstituteRepository instituteRepository,
        TrainingDirectionRepository trainingDirectionRepository,
        StatementRepository statementRepository
    ) {
        this.instituteRepository = instituteRepository;
        this.trainingDirectionRepository = trainingDirectionRepository;
        this.statementRepository = statementRepository;
    }

    public List<TrainingDirectionInfoDTO> getDirectionsWithStats(Long instituteId) {
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new NoSuchElementException("Институт не найден"));

        List<TrainingDirection> directions = trainingDirectionRepository.findByInstitute(institute);

        // Найти самую свежую дату importDate для этого института
        String latestDate = statementRepository.findMaxImportDateByInstitute(institute.getName());
        // Найти предыдущую дату
        String prevDate = statementRepository.findPrevImportDate(latestDate);

        List<TrainingDirectionInfoDTO> result = new ArrayList<>();
        for (TrainingDirection dir : directions) {
            long total = statementRepository.countByInstituteAndTrainingDirectionAndImportDate(
                institute.getName(), dir.getName(), latestDate
            );
            long priorityOne = statementRepository.countByInstituteAndTrainingDirectionAndImportDateAndPriority(
                institute.getName(), dir.getName(), latestDate, "1"
            );

            // Получаем списки заявлений по направлениям на две даты
            List<com.example.backend.model.Statement> currentStatements = statementRepository.findByTrainingDirectionAndImportDateOrderByTotalScoreDesc(dir.getName(), latestDate);
            List<com.example.backend.model.Statement> prevStatements = prevDate != null ? statementRepository.findByTrainingDirectionAndImportDateOrderByTotalScoreDesc(dir.getName(), prevDate) : new ArrayList<>();

            // Считаем новые и пропавшие заявления по personalNumber
            Set<String> currentSet = new HashSet<>();
            for (var s : currentStatements) currentSet.add(s.getPersonalNumber());
            Set<String> prevSet = new HashSet<>();
            for (var s : prevStatements) prevSet.add(s.getPersonalNumber());

            long newStatements = currentSet.stream().filter(pn -> !prevSet.contains(pn)).count();
            long disappearedStatements = prevSet.stream().filter(pn -> !currentSet.contains(pn)).count();

            TrainingDirectionInfoDTO dto = new TrainingDirectionInfoDTO();
            dto.setId(dir.getId());
            dto.setName(dir.getName());
            dto.setTotalStatements(total);
            dto.setPriorityOneStatements(priorityOne);
            dto.setNewStatements(newStatements);
            dto.setDisappearedStatements(disappearedStatements);
            result.add(dto);
        }
        return result;
    }
}