package com.example.backend.service;

import com.example.backend.dto.TrainingDirectionInfoDTO;
import com.example.backend.model.DirectionDailyStats;
import com.example.backend.model.Institute;
import com.example.backend.model.TrainingDirection;
import com.example.backend.repository.DirectionDailyStatsRepository;
import com.example.backend.repository.InstituteRepository;
import com.example.backend.repository.TrainingDirectionRepository;
import com.example.backend.repository.StatementRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InstituteService {
    private final InstituteRepository instituteRepository;
    private final TrainingDirectionRepository trainingDirectionRepository;
    private final StatementRepository statementRepository;
    private final DirectionDailyStatsRepository directionDailyStatsRepository;

    public InstituteService(
        InstituteRepository instituteRepository,
        TrainingDirectionRepository trainingDirectionRepository,
        StatementRepository statementRepository,
        DirectionDailyStatsRepository directionDailyStatsRepository
    ) {
        this.instituteRepository = instituteRepository;
        this.trainingDirectionRepository = trainingDirectionRepository;
        this.statementRepository = statementRepository;
        this.directionDailyStatsRepository = directionDailyStatsRepository;
    }

    public List<TrainingDirectionInfoDTO> getDirectionsWithStats(Long instituteId) {
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new NoSuchElementException("Институт не найден"));

        List<TrainingDirection> directions = trainingDirectionRepository.findByInstitute(institute);

        String latestDate = statementRepository.findMaxImportDateByInstitute(institute.getName());
        String prevDate = statementRepository.findPrevImportDate(latestDate);

        List<TrainingDirectionInfoDTO> result = new ArrayList<>();
        for (TrainingDirection dir : directions) {
            // Получаем списки заявлений по направлениям на две даты
            List<com.example.backend.model.Statement> currentStatements = statementRepository.findByTrainingDirectionAndImportDateOrderByTotalScoreDesc(dir.getName(), latestDate);
            List<com.example.backend.model.Statement> prevStatements = prevDate != null ? statementRepository.findByTrainingDirectionAndImportDateOrderByTotalScoreDesc(dir.getName(), prevDate) : new ArrayList<>();

            // Для поиска новых и пропавших заявлений
            Set<String> currentSet = currentStatements.stream().map(s -> s.getPersonalNumber()).collect(Collectors.toSet());
            Set<String> prevSet = prevStatements.stream().map(s -> s.getPersonalNumber()).collect(Collectors.toSet());

            // 1) total
            long total = currentStatements.size();
            long newTotal = currentStatements.stream()
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !prevSet.contains(pn))
                .count();
            long disappearedTotal = prevStatements.stream()
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !currentSet.contains(pn))
                .count();

            // 2) totalFinalConsent
            List<com.example.backend.model.Statement> currentFinalConsent = currentStatements.stream()
                .filter(s -> "Да".equalsIgnoreCase(s.getFinalConsent()))
                .collect(Collectors.toList());
            long totalFinalConsent = currentFinalConsent.size();
            long newTotalFinalConsent = currentFinalConsent.stream()
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !prevSet.contains(pn))
                .count();
            long disappearedTotalFinalConsent = prevStatements.stream()
                .filter(s -> "Да".equalsIgnoreCase(s.getFinalConsent()))
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !currentSet.contains(pn))
                .count();

            // 3) totalPriorityOne
            List<com.example.backend.model.Statement> currentPriorityOne = currentStatements.stream()
                .filter(s -> "1".equals(String.valueOf(s.getPriority())))
                .collect(Collectors.toList());
            long totalPriorityOne = currentPriorityOne.size();
            long newTotalPriorityOne = currentPriorityOne.stream()
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !prevSet.contains(pn))
                .count();
            long disappearedTotalPriorityOne = prevStatements.stream()
                .filter(s -> "1".equals(String.valueOf(s.getPriority())))
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !currentSet.contains(pn))
                .count();

            // 4) totalPriorityOneFinalConsent (admissionType != "По договору")
            List<com.example.backend.model.Statement> currentPriorityOneFinalConsent = currentStatements.stream()
                .filter(s -> "1".equals(String.valueOf(s.getPriority()))
                    && "Да".equalsIgnoreCase(s.getFinalConsent())
                    && !"По договору".equals(s.getAdmissionType()))
                .collect(Collectors.toList());
            long totalPriorityOneFinalConsent = currentPriorityOneFinalConsent.size();
            long newTotalPriorityOneFinalConsent = currentPriorityOneFinalConsent.stream()
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !prevSet.contains(pn))
                .count();
            long disappearedTotalPriorityOneFinalConsent = prevStatements.stream()
                .filter(s -> "1".equals(String.valueOf(s.getPriority()))
                    && "Да".equalsIgnoreCase(s.getFinalConsent())
                    && !"По договору".equals(s.getAdmissionType()))
                .map(s -> s.getPersonalNumber())
                .filter(pn -> !currentSet.contains(pn))
                .count();

            // --- Заполнение DTO ---
            TrainingDirectionInfoDTO dto = new TrainingDirectionInfoDTO();
            dto.setId(dir.getId());
            dto.setName(dir.getName());

            dto.setTotalStatements(total);
            dto.setNewStatements(newTotal);
            dto.setDisappearedStatements(disappearedTotal);

            dto.setTotalFinalConsent(totalFinalConsent);
            dto.setNewTotalFinalConsent(newTotalFinalConsent);
            dto.setDisappearedTotalFinalConsent(disappearedTotalFinalConsent);

            dto.setPriorityOneStatements(totalPriorityOne);
            dto.setNewPriorityOneStatements(newTotalPriorityOne);
            dto.setDisappearedPriorityOneStatements(disappearedTotalPriorityOne);

            dto.setPriorityOneFinalConsent(totalPriorityOneFinalConsent);
            dto.setNewPriorityOneFinalConsent(newTotalPriorityOneFinalConsent);
            dto.setDisappearedPriorityOneFinalConsent(disappearedTotalPriorityOneFinalConsent);

            // Универсальный метод для подсчёта total/новых/пропавших по admissionType
            String[] types = {
                "Общий конкурс",
                "По договору",
                "В рамках квоты лиц, имеющих особые права",
                "Отдельная квота",
                "Целевой прием"
            };

            Map<String, Long> totalByType = new HashMap<>();
            Map<String, Long> newByType = new HashMap<>();
            Map<String, Long> disappearedByType = new HashMap<>();

            for (String type : types) {
                // total только для заявлений с этим admissionType
                long totalType = currentStatements.stream()
                        .filter(s -> type.equals(s.getAdmissionType()))
                        .count();
                totalByType.put(type, totalType);

                // новые заявления с этим admissionType
                long newType = currentStatements.stream()
                        .filter(s -> type.equals(s.getAdmissionType()))
                        .map(s -> s.getPersonalNumber())
                        .filter(pn -> !prevSet.contains(pn))
                        .count();
                newByType.put(type, newType);

                // пропавшие заявления с этим admissionType
                long disappearedType = prevStatements.stream()
                        .filter(s -> type.equals(s.getAdmissionType()))
                        .map(s -> s.getPersonalNumber())
                        .filter(pn -> !currentSet.contains(pn))
                        .count();
                disappearedByType.put(type, disappearedType);
            }

            // Пример: для "Общий конкурс"
            dto.setTotalCommon(totalByType.get("Общий конкурс"));
            dto.setNewCommon(newByType.get("Общий конкурс"));
            dto.setDisappearedCommon(disappearedByType.get("Общий конкурс"));

            // Для "По договору"
            dto.setTotalContract(totalByType.get("По договору"));
            dto.setNewContract(newByType.get("По договору"));
            dto.setDisappearedContract(disappearedByType.get("По договору"));

            // Для "В рамках квоты лиц, имеющих особые права"
            dto.setTotalQuotaSpecial(totalByType.get("В рамках квоты лиц, имеющих особые права"));
            dto.setNewQuotaSpecial(newByType.get("В рамках квоты лиц, имеющих особые права"));
            dto.setDisappearedQuotaSpecial(disappearedByType.get("В рамках квоты лиц, имеющих особые права"));

            // Для "Отдельная квота"
            dto.setTotalQuotaSeparate(totalByType.get("Отдельная квота"));
            dto.setNewQuotaSeparate(newByType.get("Отдельная квота"));
            dto.setDisappearedQuotaSeparate(disappearedByType.get("Отдельная квота"));

            // Для "Целевой прием"
            dto.setTotalTarget(totalByType.get("Целевой прием"));
            dto.setNewTarget(newByType.get("Целевой прием"));
            dto.setDisappearedTarget(disappearedByType.get("Целевой прием"));

            result.add(dto);
        }
        return result;
    }

    public void saveDirectionDailyStats(Map<String, Long> currentCounts, int batchSize) {
        List<DirectionDailyStats> statsBatch = new ArrayList<>();
        for (String key : currentCounts.keySet()) {
            DirectionDailyStats stats = new DirectionDailyStats();
            // ...настройка stats в зависимости от ключа и значений currentCounts...

            statsBatch.add(stats);
            if (statsBatch.size() >= batchSize) {
                directionDailyStatsRepository.saveAll(statsBatch);
                directionDailyStatsRepository.flush();
                statsBatch.clear();
            }
        }
        if (!statsBatch.isEmpty()) {
            directionDailyStatsRepository.saveAll(statsBatch);
            directionDailyStatsRepository.flush();
        }
    }
}