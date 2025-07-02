package com.example.backend.dto;

public class TrainingDirectionInfoDTO {
    private Long id;
    private String name;
    private long totalStatements;
    private long priorityOneStatements;
    private long newStatements;
    private long disappearedStatements;

    // Новые поля для расширенной статистики
    private long totalFinalConsent;
    private long priorityOneFinalConsent;

    // Общий конкурс
    private long totalCommon;
    private long newCommon;
    private long disappearedCommon;

    // По договору
    private long totalContract;
    private long newContract;
    private long disappearedContract;

    // В рамках квоты лиц, имеющих особые права
    private long totalQuotaSpecial;
    private long newQuotaSpecial;
    private long disappearedQuotaSpecial;

    // Отдельная квота
    private long totalQuotaSeparate;
    private long newQuotaSeparate;
    private long disappearedQuotaSeparate;

    // Целевой прием
    private long totalTarget;
    private long newTarget;
    private long disappearedTarget;

    // Новые поля для новых и пропавших заявлений по totalFinalConsent
    private long newTotalFinalConsent;
    private long disappearedTotalFinalConsent;

    // Новые поля для новых и пропавших заявлений по priorityOneStatements
    private long newPriorityOneStatements;
    private long disappearedPriorityOneStatements;

    // Новые поля для новых и пропавших заявлений по priorityOneFinalConsent
    private long newPriorityOneFinalConsent;
    private long disappearedPriorityOneFinalConsent;

    public TrainingDirectionInfoDTO() {}

    public TrainingDirectionInfoDTO(Long id, String name, long totalStatements, long priorityOneStatements, long newStatements, long disappearedStatements) {
        this.id = id;
        this.name = name;
        this.totalStatements = totalStatements;
        this.priorityOneStatements = priorityOneStatements;
        this.newStatements = newStatements;
        this.disappearedStatements = disappearedStatements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalStatements() {
        return totalStatements;
    }

    public void setTotalStatements(long totalStatements) {
        this.totalStatements = totalStatements;
    }

    public long getPriorityOneStatements() {
        return priorityOneStatements;
    }

    public void setPriorityOneStatements(long priorityOneStatements) {
        this.priorityOneStatements = priorityOneStatements;
    }

    public long getNewStatements() {
        return newStatements;
    }

    public void setNewStatements(long newStatements) {
        this.newStatements = newStatements;
    }

    public long getDisappearedStatements() {
        return disappearedStatements;
    }

    public void setDisappearedStatements(long disappearedStatements) {
        this.disappearedStatements = disappearedStatements;
    }

    // --- Геттеры и сеттеры ---

    public long getTotalFinalConsent() {
        return totalFinalConsent;
    }

    public void setTotalFinalConsent(long totalFinalConsent) {
        this.totalFinalConsent = totalFinalConsent;
    }

    public long getPriorityOneFinalConsent() {
        return priorityOneFinalConsent;
    }

    public void setPriorityOneFinalConsent(long priorityOneFinalConsent) {
        this.priorityOneFinalConsent = priorityOneFinalConsent;
    }

    public long getTotalCommon() {
        return totalCommon;
    }

    public void setTotalCommon(long totalCommon) {
        this.totalCommon = totalCommon;
    }

    public long getNewCommon() {
        return newCommon;
    }

    public void setNewCommon(long newCommon) {
        this.newCommon = newCommon;
    }

    public long getDisappearedCommon() {
        return disappearedCommon;
    }

    public void setDisappearedCommon(long disappearedCommon) {
        this.disappearedCommon = disappearedCommon;
    }

    public long getTotalContract() {
        return totalContract;
    }

    public void setTotalContract(long totalContract) {
        this.totalContract = totalContract;
    }

    public long getNewContract() {
        return newContract;
    }

    public void setNewContract(long newContract) {
        this.newContract = newContract;
    }

    public long getDisappearedContract() {
        return disappearedContract;
    }

    public void setDisappearedContract(long disappearedContract) {
        this.disappearedContract = disappearedContract;
    }

    public long getTotalQuotaSpecial() {
        return totalQuotaSpecial;
    }

    public void setTotalQuotaSpecial(long totalQuotaSpecial) {
        this.totalQuotaSpecial = totalQuotaSpecial;
    }

    public long getNewQuotaSpecial() {
        return newQuotaSpecial;
    }

    public void setNewQuotaSpecial(long newQuotaSpecial) {
        this.newQuotaSpecial = newQuotaSpecial;
    }

    public long getDisappearedQuotaSpecial() {
        return disappearedQuotaSpecial;
    }

    public void setDisappearedQuotaSpecial(long disappearedQuotaSpecial) {
        this.disappearedQuotaSpecial = disappearedQuotaSpecial;
    }

    public long getTotalQuotaSeparate() {
        return totalQuotaSeparate;
    }

    public void setTotalQuotaSeparate(long totalQuotaSeparate) {
        this.totalQuotaSeparate = totalQuotaSeparate;
    }

    public long getNewQuotaSeparate() {
        return newQuotaSeparate;
    }

    public void setNewQuotaSeparate(long newQuotaSeparate) {
        this.newQuotaSeparate = newQuotaSeparate;
    }

    public long getDisappearedQuotaSeparate() {
        return disappearedQuotaSeparate;
    }

    public void setDisappearedQuotaSeparate(long disappearedQuotaSeparate) {
        this.disappearedQuotaSeparate = disappearedQuotaSeparate;
    }

    public long getTotalTarget() {
        return totalTarget;
    }

    public void setTotalTarget(long totalTarget) {
        this.totalTarget = totalTarget;
    }

    public long getNewTarget() {
        return newTarget;
    }

    public void setNewTarget(long newTarget) {
        this.newTarget = newTarget;
    }

    public long getDisappearedTarget() {
        return disappearedTarget;
    }

    public void setDisappearedTarget(long disappearedTarget) {
        this.disappearedTarget = disappearedTarget;
    }

    public long getNewTotalFinalConsent() {
        return newTotalFinalConsent;
    }

    public void setNewTotalFinalConsent(long newTotalFinalConsent) {
        this.newTotalFinalConsent = newTotalFinalConsent;
    }

    public long getDisappearedTotalFinalConsent() {
        return disappearedTotalFinalConsent;
    }

    public void setDisappearedTotalFinalConsent(long disappearedTotalFinalConsent) {
        this.disappearedTotalFinalConsent = disappearedTotalFinalConsent;
    }

    public long getNewPriorityOneStatements() {
        return newPriorityOneStatements;
    }

    public void setNewPriorityOneStatements(long newPriorityOneStatements) {
        this.newPriorityOneStatements = newPriorityOneStatements;
    }

    public long getDisappearedPriorityOneStatements() {
        return disappearedPriorityOneStatements;
    }

    public void setDisappearedPriorityOneStatements(long disappearedPriorityOneStatements) {
        this.disappearedPriorityOneStatements = disappearedPriorityOneStatements;
    }

    public long getNewPriorityOneFinalConsent() {
        return newPriorityOneFinalConsent;
    }

    public void setNewPriorityOneFinalConsent(long newPriorityOneFinalConsent) {
        this.newPriorityOneFinalConsent = newPriorityOneFinalConsent;
    }

    public long getDisappearedPriorityOneFinalConsent() {
        return disappearedPriorityOneFinalConsent;
    }

    public void setDisappearedPriorityOneFinalConsent(long disappearedPriorityOneFinalConsent) {
        this.disappearedPriorityOneFinalConsent = disappearedPriorityOneFinalConsent;
    }

}