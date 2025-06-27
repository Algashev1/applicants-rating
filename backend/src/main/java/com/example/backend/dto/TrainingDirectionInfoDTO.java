package com.example.backend.dto;

public class TrainingDirectionInfoDTO {
    private Long id;
    private String name;
    private long totalStatements;
    private long priorityOneStatements;
    private long newStatements;
    private long disappearedStatements;

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
}