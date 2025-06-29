package com.example.backend.mapper;

import com.example.backend.dto.StatementPublicDto;
import com.example.backend.model.Statement;
import org.springframework.stereotype.Component;

@Component
public class StatementMapper {

    public StatementPublicDto toPublicDto(Statement statement) {
        return new StatementPublicDto(
            statement.getAdmissionType(),
            statement.getPriority(),
            statement.getOverallPriority(),
            statement.getTrainingDirection(),
            statement.getTargetedAdmission(),
            statement.getSeparateQuota(),
            statement.getSpecialQuota(),
            statement.getSubmissionDate(),
            statement.getTotalScore(),
            statement.getIndividualAchievementAdvantageTotal(),
            statement.getCostReimbursementType(),
            statement.getImportDate(),
            statement.getSpCode()
        );
    }
}
