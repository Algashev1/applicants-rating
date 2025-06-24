package com.example.backend.repository;

import com.example.backend.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Long> {
    List<Statement> findByPersonalNumber(String personalNumber);
    Statement findByPersonalNumberAndImportDateAndInstituteAndTrainingDirection(String personalNumber, String importDate, String institute, String trainingDirection);
    List<Statement> findAllByPersonalNumberAndImportDateAndInstituteAndTrainingDirectionAndCostReimbursementType(
        String personalNumber, String importDate, String institute, String trainingDirection, String costReimbursementType);
    List<Statement> findAllByPersonalNumberAndImportDateAndInstituteAndTrainingDirectionAndCostReimbursementTypeAndTargetedAdmissionAndSeparateQuotaAndSpecialQuota(
        String personalNumber, String importDate, String institute, String trainingDirection, String costReimbursementType,
        String targetedAdmission, String separateQuota, String specialQuota);

    @Query("select max(s.importDate) from Statement s")
    String findMaxImportDate();

    @Query("SELECT max(s.importDate) FROM Statement s WHERE s.institute = :instituteName")
    String findMaxImportDateByInstitute(@Param("instituteName") String instituteName);

    long countByInstituteAndTrainingDirectionAndImportDate(String institute, String trainingDirection, String importDate);

    long countByInstituteAndTrainingDirectionAndImportDateAndPriority(String institute, String trainingDirection, String importDate, String priority);

    List<Statement> findByTrainingDirectionAndImportDateOrderByTotalScoreDesc(String trainingDirection, String importDate);

    List<Statement> findByTrainingDirectionAndImportDateAndPriorityOrderByTotalScoreDesc(String trainingDirection, String importDate, String priority);

    @Query("SELECT max(s.importDate) FROM Statement s WHERE s.importDate < :latestDate")
    String findPrevImportDate(@Param("latestDate") String latestDate);

    @Query("SELECT DISTINCT CAST(s.importDate AS string) FROM Statement s ORDER BY CAST(s.importDate AS string)")
    List<String> findDistinctDates();

    @Query("SELECT s FROM Statement s WHERE s.trainingDirection = :trainingDirection AND CAST(s.importDate AS string) = :date"
         + " AND (:onlyPriorityOne = false OR s.priority = '1')")
    List<Statement> findByDirectionAndDate(@Param("trainingDirection") String trainingDirection,
                                           @Param("date") String date,
                                           @Param("onlyPriorityOne") boolean onlyPriorityOne);

    @Query(value = "SELECT DISTINCT CAST(import_date AS text) FROM statements WHERE CAST(import_date AS text) < :date ORDER BY import_date DESC LIMIT 1", nativeQuery = true)
    String findPreviousDate(@Param("date") String date);
}