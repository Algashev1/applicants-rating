package com.example.backend.repository;

import com.example.backend.model.DirectionDailyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DirectionDailyStatsRepository extends JpaRepository<DirectionDailyStats, Long> {
    List<DirectionDailyStats> findByInstituteNameAndDirectionNameOrderByImportDateDesc(String instituteName, String directionName);
    DirectionDailyStats findByInstituteNameAndDirectionNameAndImportDate(String instituteName, String directionName, LocalDate importDate);
    List<DirectionDailyStats> findByImportDate(LocalDate importDate);

    default Map<String, Integer> getCurrentCounts() {
        Map<String, Integer> currentCounts = new HashMap<>();
        List<DirectionDailyStats> allStats = findAll();
        for (DirectionDailyStats stats : allStats) {
            String key = stats.getInstituteName() + "_" + stats.getDirectionName();
            currentCounts.put(key, currentCounts.getOrDefault(key, 0) + 1);
        }
        return currentCounts;
    }
}
