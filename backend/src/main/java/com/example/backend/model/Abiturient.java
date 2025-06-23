package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "abiturients", uniqueConstraints = @UniqueConstraint(columnNames = {"personalNumber"}))
public class Abiturient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Новое поле ФИО
    private String fullName;
    
    // Пол (например "М" или "Ж")
    private String gender;
    
    // Дата рождения
    private LocalDate birthDate;
    
    // личный номер – уникальное поле
    @Column(name = "personal_number", nullable = false)
    private String personalNumber;
    
    // Льготы
    private String benefits;
    
    // Контактный телефон
    private String contactPhone;
    
    // Домашний телефон
    private String homePhone;
    
    // Мобильный телефон
    private String mobilePhone;
    
    // E-mail
    private String email;
    
    // Иностранное гражданство (true – да, false – нет)
    private Boolean foreignCitizenship;

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getPersonalNumber() {
        return personalNumber;
    }
    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }
    
    public String getBenefits() {
        return benefits;
    }
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public String getHomePhone() {
        return homePhone;
    }
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
    
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getForeignCitizenship() {
        return foreignCitizenship;
    }
    public void setForeignCitizenship(Boolean foreignCitizenship) {
        this.foreignCitizenship = foreignCitizenship;
    }
}