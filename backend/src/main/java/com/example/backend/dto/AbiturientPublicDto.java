package com.example.backend.dto;

public class AbiturientPublicDto {

    private String personalNumber;
    private String spCode;
    private String gender;
    private String benefits;
    private Boolean foreignCitizenship;

    public AbiturientPublicDto() {
    }

    public AbiturientPublicDto(String personalNumber, String spCode, String gender, String benefits, Boolean foreignCitizenship) {
        this.personalNumber = personalNumber;
        this.spCode = spCode;
        this.gender = gender;
        this.benefits = benefits;
        this.foreignCitizenship = foreignCitizenship;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Boolean getForeignCitizenship() {
        return foreignCitizenship;
    }

    public void setForeignCitizenship(Boolean foreignCitizenship) {
        this.foreignCitizenship = foreignCitizenship;
    }
}
