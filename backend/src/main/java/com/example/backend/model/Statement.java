package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statements")
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 0) дата импорта
    private String importDate;
    // 1) личный номер
    @Column(nullable = false)
    private String personalNumber;

    private String spCode;
    // 2) дата подачи заявления
    private String submissionDate;
    // 3) преимущественное право
    private String preferentialRight;
    // 4) Вид возмещения затрат
    private String costReimbursementType;
    // 5) вид приёма
    private String admissionType;
    // 6) дополнительный набор
    private String additionalSet;
    // 7) приоритет
    private String priority;
    // 8) сквозной приоритет
    private String overallPriority;
    // 9) институт
    private String institute;
    // 10) направление подготовки
    private String trainingDirection;
    // 11) набор ОП
    private String opSet;
    // 12) Выбранные ОП
    private String chosenOp;
    // 13) Целевой приём
    private String targetedAdmission;
    // 14) Без вступительных испытаний
    private String noEntranceExams;
    // 15) Отдельная квота
    private String separateQuota;
    // 16) Особая квота
    private String specialQuota;
    // 17) Состояние выбранного конкурса
    private String contestStatus;
    // 18) Сдан оригинал
    private String originalSubmitted;
    // 19) Согласие на зачисление
    private String enrollmentConsent;
    // 20) Дата согласия
    private String consentDate;
    // 21) Итоговое согласие
    private String finalConsent;
    // 22) Сумма баллов
    private String totalScore;
    // 23) Результаты вступительных испытаний
    private String examResults;
    // 24) р.я
    private String ry;
    // 25) фр.я
    private String frya;
    // 26) общ
    private String common;
    // 27) лит
    private String literature;
    // 28) инф
    private String informatics;
    // 29) м.проф
    private String mpProfile;
    // 30) эл.м.проф
    private String elmpProfile;
    // 31) ос.ин.р.проф
    private String osInRpProfile;
    // 32) инф.проф
    private String informaticsProfile;
    // 33) мат
    private String math;
    // 34) био.проф.
    private String bioProfile;
    // 35) лит.проф.
    private String litProfile;
    // 36) ист.проф.
    private String historyProfile;
    // 37) общ.проф.
    private String commonProfile;
    // 38) мат.во
    private String matVo;
    // 39) физ.во
    private String physVo;
    // 40) общ.во
    private String commonVo;
    // 41) ист.во
    private String historyVo;
    // 42) инф.во
    private String informaticsVo;
    // 43) англ.во
    private String englishVo;
    // 44) нем.во
    private String germanVo;
    // 45) лит.во
    private String litVo;
    // 46) био.во
    private String bioVo;
    // 47) хим.во
    private String chemVo;
    // 48) Творч.исп.
    private String creativeExam;
    // 49) физ
    private String phys;
    // 50) хим
    private String chem;
    // 51) био
    private String bio;
    // 52) ист
    private String history;
    // 53) ан.я
    private String anya;
    // 54) нем.я
    private String nemya;
    // 56) способ подачи документов
    private String documentSubmissionMethod;
    // 57) способ возрата документов
    private String documentReturnMethod;
    // 58) олимпиады
    private String olympiads;
    // 59) результаты ЕГЭ
    private String egeResults;
    // 60) Ря
    private String ry2;
    // 61) М
    private String m;
    // 62) Ф
    private String f;
    // 63) Х
    private String h;
    // 64) Б
    private String b;
    // 65) И
    private String i;
    // 66) Г
    private String g;
    // 67) Ая
    private String aya;
    // 68) Ня
    private String nya;
    // 69) Фя
    private String fya;
    // 70) О
    private String o;
    // 71) Л
    private String l;
    // 72) Ия
    private String iya;
    // 73) ИКТ
    private String ikt;
    // 74) Кя
    private String kya;
    // 75) ИТя
    private String ity;
    // 76) Статус результатов ЕГЭ
    private String egeStatus;
    // 78) Баллы за индивидуальные достижения
    private String individualAchievementScore;
    // 79) Сумма баллов за индивидуальные достижения
    private String individualAchievementTotal;
    // 80) Баллы за индивидуальные достижения, учитываемые как преимущество
    private String individualAchievementAdvantageScore;
    // 81) Сумма баллов за индивидуальные достижения, учитываемые как преимущество
    private String individualAchievementAdvantageTotal;

    @Column
    private String fullName;

    // Новые поля для направлений с приоритетом
    private String priority1Direction;
    private String priority2Direction;
    private String priority3Direction;
    private String priority4Direction;
    private String priority5Direction;

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getImportDate() {
        return importDate;
    }
    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }
    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    public String getSpCode() {
        return spCode;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getPreferentialRight() {
        return preferentialRight;
    }
    public void setPreferentialRight(String preferentialRight) {
        this.preferentialRight = preferentialRight;
    }

    public String getCostReimbursementType() {
        return costReimbursementType;
    }
    public void setCostReimbursementType(String costReimbursementType) {
        this.costReimbursementType = costReimbursementType;
    }

    public String getAdmissionType() {
        return admissionType;
    }
    public void setAdmissionType(String admissionType) {
        this.admissionType = admissionType;
    }

    public String getAdditionalSet() {
        return additionalSet;
    }
    public void setAdditionalSet(String additionalSet) {
        this.additionalSet = additionalSet;
    }

    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOverallPriority() {
        return overallPriority;
    }
    public void setOverallPriority(String overallPriority) {
        this.overallPriority = overallPriority;
    }

    public String getInstitute() {
        return institute;
    }
    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getTrainingDirection() {
        return trainingDirection;
    }
    public void setTrainingDirection(String trainingDirection) {
        this.trainingDirection = trainingDirection;
    }

    public String getOpSet() {
        return opSet;
    }
    public void setOpSet(String opSet) {
        this.opSet = opSet;
    }

    public String getChosenOp() {
        return chosenOp;
    }
    public void setChosenOp(String chosenOp) {
        this.chosenOp = chosenOp;
    }

    public String getTargetedAdmission() {
        return targetedAdmission;
    }
    public void setTargetedAdmission(String targetedAdmission) {
        this.targetedAdmission = targetedAdmission;
    }

    public String getNoEntranceExams() {
        return noEntranceExams;
    }
    public void setNoEntranceExams(String noEntranceExams) {
        this.noEntranceExams = noEntranceExams;
    }

    public String getSeparateQuota() {
        return separateQuota;
    }
    public void setSeparateQuota(String separateQuota) {
        this.separateQuota = separateQuota;
    }

    public String getSpecialQuota() {
        return specialQuota;
    }
    public void setSpecialQuota(String specialQuota) {
        this.specialQuota = specialQuota;
    }

    public String getContestStatus() {
        return contestStatus;
    }
    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    public String getOriginalSubmitted() {
        return originalSubmitted;
    }
    public void setOriginalSubmitted(String originalSubmitted) {
        this.originalSubmitted = originalSubmitted;
    }

    public String getEnrollmentConsent() {
        return enrollmentConsent;
    }
    public void setEnrollmentConsent(String enrollmentConsent) {
        this.enrollmentConsent = enrollmentConsent;
    }

    public String getConsentDate() {
        return consentDate;
    }
    public void setConsentDate(String consentDate) {
        this.consentDate = consentDate;
    }

    public String getFinalConsent() {
        return finalConsent;
    }
    public void setFinalConsent(String finalConsent) {
        this.finalConsent = finalConsent;
    }

    public String getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getExamResults() {
        return examResults;
    }
    public void setExamResults(String examResults) {
        this.examResults = examResults;
    }

    public String getRy() {
        return ry;
    }
    public void setRy(String ry) {
        this.ry = ry;
    }

    public String getFrya() {
        return frya;
    }
    public void setFrya(String frya) {
        this.frya = frya;
    }

    public String getCommon() {
        return common;
    }
    public void setCommon(String common) {
        this.common = common;
    }

    public String getLiterature() {
        return literature;
    }
    public void setLiterature(String literature) {
        this.literature = literature;
    }

    public String getInformatics() {
        return informatics;
    }
    public void setInformatics(String informatics) {
        this.informatics = informatics;
    }

    public String getMpProfile() {
        return mpProfile;
    }
    public void setMpProfile(String mpProfile) {
        this.mpProfile = mpProfile;
    }

    public String getElmpProfile() {
        return elmpProfile;
    }
    public void setElmpProfile(String elmpProfile) {
        this.elmpProfile = elmpProfile;
    }

    public String getOsInRpProfile() {
        return osInRpProfile;
    }
    public void setOsInRpProfile(String osInRpProfile) {
        this.osInRpProfile = osInRpProfile;
    }

    public String getInformaticsProfile() {
        return informaticsProfile;
    }
    public void setInformaticsProfile(String informaticsProfile) {
        this.informaticsProfile = informaticsProfile;
    }

    public String getMath() {
        return math;
    }
    public void setMath(String math) {
        this.math = math;
    }

    public String getBioProfile() {
        return bioProfile;
    }
    public void setBioProfile(String bioProfile) {
        this.bioProfile = bioProfile;
    }

    public String getLitProfile() {
        return litProfile;
    }
    public void setLitProfile(String litProfile) {
        this.litProfile = litProfile;
    }

    public String getHistoryProfile() {
        return historyProfile;
    }
    public void setHistoryProfile(String historyProfile) {
        this.historyProfile = historyProfile;
    }

    public String getCommonProfile() {
        return commonProfile;
    }
    public void setCommonProfile(String commonProfile) {
        this.commonProfile = commonProfile;
    }

    public String getMatVo() {
        return matVo;
    }
    public void setMatVo(String matVo) {
        this.matVo = matVo;
    }

    public String getPhysVo() {
        return physVo;
    }
    public void setPhysVo(String physVo) {
        this.physVo = physVo;
    }

    public String getCommonVo() {
        return commonVo;
    }
    public void setCommonVo(String commonVo) {
        this.commonVo = commonVo;
    }

    public String getHistoryVo() {
        return historyVo;
    }
    public void setHistoryVo(String historyVo) {
        this.historyVo = historyVo;
    }

    public String getInformaticsVo() {
        return informaticsVo;
    }
    public void setInformaticsVo(String informaticsVo) {
        this.informaticsVo = informaticsVo;
    }

    public String getEnglishVo() {
        return englishVo;
    }
    public void setEnglishVo(String englishVo) {
        this.englishVo = englishVo;
    }

    public String getGermanVo() {
        return germanVo;
    }
    public void setGermanVo(String germanVo) {
        this.germanVo = germanVo;
    }

    public String getLitVo() {
        return litVo;
    }
    public void setLitVo(String litVo) {
        this.litVo = litVo;
    }

    public String getBioVo() {
        return bioVo;
    }
    public void setBioVo(String bioVo) {
        this.bioVo = bioVo;
    }

    public String getChemVo() {
        return chemVo;
    }
    public void setChemVo(String chemVo) {
        this.chemVo = chemVo;
    }

    public String getCreativeExam() {
        return creativeExam;
    }
    public void setCreativeExam(String creativeExam) {
        this.creativeExam = creativeExam;
    }

    public String getPhys() {
        return phys;
    }
    public void setPhys(String phys) {
        this.phys = phys;
    }

    public String getChem() {
        return chem;
    }
    public void setChem(String chem) {
        this.chem = chem;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }

    public String getAnya() {
        return anya;
    }
    public void setAnya(String anya) {
        this.anya = anya;
    }

    public String getNemya() {
        return nemya;
    }
    public void setNemya(String nemya) {
        this.nemya = nemya;
    }

    public String getDocumentSubmissionMethod() {
        return documentSubmissionMethod;
    }
    public void setDocumentSubmissionMethod(String documentSubmissionMethod) {
        this.documentSubmissionMethod = documentSubmissionMethod;
    }

    public String getDocumentReturnMethod() {
        return documentReturnMethod;
    }
    public void setDocumentReturnMethod(String documentReturnMethod) {
        this.documentReturnMethod = documentReturnMethod;
    }

    public String getOlympiads() {
        return olympiads;
    }
    public void setOlympiads(String olympiads) {
        this.olympiads = olympiads;
    }

    public String getEgeResults() {
        return egeResults;
    }
    public void setEgeResults(String egeResults) {
        this.egeResults = egeResults;
    }

    public String getRy2() {
        return ry2;
    }
    public void setRy2(String ry2) {
        this.ry2 = ry2;
    }

    public String getM() {
        return m;
    }
    public void setM(String m) {
        this.m = m;
    }

    public String getF() {
        return f;
    }
    public void setF(String f) {
        this.f = f;
    }

    public String getH() {
        return h;
    }
    public void setH(String h) {
        this.h = h;
    }

    public String getB() {
        return b;
    }
    public void setB(String b) {
        this.b = b;
    }

    public String getI() {
        return i;
    }
    public void setI(String i) {
        this.i = i;
    }

    public String getG() {
        return g;
    }
    public void setG(String g) {
        this.g = g;
    }

    public String getAya() {
        return aya;
    }
    public void setAya(String aya) {
        this.aya = aya;
    }

    public String getNya() {
        return nya;
    }
    public void setNya(String nya) {
        this.nya = nya;
    }

    public String getFya() {
        return fya;
    }
    public void setFya(String fya) {
        this.fya = fya;
    }

    public String getO() {
        return o;
    }
    public void setO(String o) {
        this.o = o;
    }

    public String getL() {
        return l;
    }
    public void setL(String l) {
        this.l = l;
    }

    public String getIya() {
        return iya;
    }
    public void setIya(String iya) {
        this.iya = iya;
    }

    public String getIkt() {
        return ikt;
    }
    public void setIkt(String ikt) {
        this.ikt = ikt;
    }

    public String getKya() {
        return kya;
    }
    public void setKya(String kya) {
        this.kya = kya;
    }

    public String getIty() {
        return ity;
    }
    public void setIty(String ity) {
        this.ity = ity;
    }

    public String getEgeStatus() {
        return egeStatus;
    }
    public void setEgeStatus(String egeStatus) {
        this.egeStatus = egeStatus;
    }

    public String getIndividualAchievementScore() {
        return individualAchievementScore;
    }
    public void setIndividualAchievementScore(String individualAchievementScore) {
        this.individualAchievementScore = individualAchievementScore;
    }

    public String getIndividualAchievementTotal() {
        return individualAchievementTotal;
    }
    public void setIndividualAchievementTotal(String individualAchievementTotal) {
        this.individualAchievementTotal = individualAchievementTotal;
    }

    public String getIndividualAchievementAdvantageScore() {
        return individualAchievementAdvantageScore;
    }
    public void setIndividualAchievementAdvantageScore(String individualAchievementAdvantageScore) {
        this.individualAchievementAdvantageScore = individualAchievementAdvantageScore;
    }

    public String getIndividualAchievementAdvantageTotal() {
        return individualAchievementAdvantageTotal;
    }
    public void setIndividualAchievementAdvantageTotal(String individualAchievementAdvantageTotal) {
        this.individualAchievementAdvantageTotal = individualAchievementAdvantageTotal;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPriority1Direction(String priority1Direction) {
        this.priority1Direction = priority1Direction;
    }

    public String getPriority1Direction() {
        return priority1Direction;
    }

    public void setPriority2Direction(String priority2Direction) {
        this.priority2Direction = priority2Direction;
    }

    public String getPriority2Direction() {
        return priority2Direction;
    }

    public void setPriority3Direction(String priority3Direction) {
        this.priority3Direction = priority3Direction;
    }

    public String getPriority3Direction() {
        return priority3Direction;
    }
    
    public void setPriority4Direction(String priority4Direction) {
        this.priority4Direction = priority4Direction;
    }

    public String getPriority4Direction() {
        return priority4Direction;
    }

    public void setPriority5Direction(String priority5Direction) {
        this.priority5Direction = priority5Direction;
    }

    public String getPriority5Direction() {
        return priority5Direction;
    }
}