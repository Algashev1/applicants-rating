package com.example.backend.dto;

public class StatementPublicDto {
    private String admissionType;
    private String priority;
    private String overallPriority;
    private String trainingDirection;
    private String targetedAdmission;
    private String separateQuota;
    private String specialQuota;
    private String submissionDate;
    private String totalScore;
    private String individualAchievementAdvantageTotal;
    private String costReimbursementType;

    private String importDate;
    private String spCode;

    public StatementPublicDto(String admissionType,
                              String priority,
                              String overallPriority,
                              String trainingDirection,
                              String targetedAdmission,
                              String separateQuota,
                              String specialQuota,
                              String submissionDate,
                              String totalScore,
                              String individualAchievementAdvantageTotal,
                              String costReimbursementType,
                              String importDate,
                              String spCode) {
        this.admissionType = admissionType;
        this.priority = priority;
        this.overallPriority = overallPriority;
        this.trainingDirection = trainingDirection;
        this.targetedAdmission = targetedAdmission;
        this.separateQuota = separateQuota;
        this.specialQuota = specialQuota;
        this.submissionDate = submissionDate;
        this.totalScore = totalScore;
        this.individualAchievementAdvantageTotal = individualAchievementAdvantageTotal;
        this.costReimbursementType = costReimbursementType;
        this.importDate = importDate;
        this. spCode = spCode;
    }

    public String getAdmissionType() { return admissionType; }
    public void setAdmissionType(String admissionType) { this.admissionType = admissionType; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getOverallPriority() { return overallPriority; }
    public void setOverallPriority(String overallPriority) { this.overallPriority = overallPriority; }

    public String getTrainingDirection() { return trainingDirection; }
    public void setTrainingDirection(String trainingDirection) { this.trainingDirection = trainingDirection; }

    public String getTargetedAdmission() { return targetedAdmission; }
    public void setTargetedAdmission(String targetedAdmission) { this.targetedAdmission = targetedAdmission; }

    public String getSeparateQuota() { return separateQuota; }
    public void setSeparateQuota(String separateQuota) { this.separateQuota = separateQuota; }

    public String getSpecialQuota() { return specialQuota; }
    public void setSpecialQuota(String specialQuota) { this.specialQuota = specialQuota; }

    public String getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(String submissionDate) { this.submissionDate = submissionDate; }

    public String getTotalScore() { return totalScore; }
    public void setTotalScore(String totalScore) { this.totalScore = totalScore; }

    public String getIndividualAchievementAdvantageTotal() { return individualAchievementAdvantageTotal; }
    public void setIndividualAchievementAdvantageTotal(String individualAchievementAdvantageTotal) { this.individualAchievementAdvantageTotal = individualAchievementAdvantageTotal; }

    public String getCostReimbursementType() { return costReimbursementType; }
    public void setCostReimbursementType(String costReimbursementType) { this.costReimbursementType = costReimbursementType; }

    public String getImportDate() { return importDate; }
    public void setImportDate(String importDate) { this.importDate = importDate; }

    public String getSpCode() { return spCode; }
    public void setSpCode(String spCode) { this.spCode = spCode; }
}
