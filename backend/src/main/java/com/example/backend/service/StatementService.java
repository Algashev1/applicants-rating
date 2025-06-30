package com.example.backend.service;

import com.example.backend.model.Statement;
import com.example.backend.repository.StatementRepository;
import com.example.backend.repository.InstituteRepository;
import com.example.backend.repository.TrainingDirectionRepository;
import com.example.backend.dto.DirectionStatementsResponse;
import com.example.backend.dto.StatementPublicDto;
import com.example.backend.mapper.StatementMapper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.time.LocalDate;
import com.example.backend.model.Institute;
import com.example.backend.model.TrainingDirection;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import com.example.backend.model.DirectionDailyStats;
import com.example.backend.repository.DirectionDailyStatsRepository;
import java.util.stream.Collectors;
import java.util.*;

@Service
public class StatementService {

    private final StatementRepository repository;
    private final InstituteRepository instituteRepository;
    private final TrainingDirectionRepository trainingDirectionRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private DirectionDailyStatsRepository directionDailyStatsRepository;

    @Autowired
    private StatementMapper statementMapper;

    public StatementService(
        StatementRepository repository,
        InstituteRepository instituteRepository,
        TrainingDirectionRepository trainingDirectionRepository
    ) {
        this.repository = repository;
        this.instituteRepository = instituteRepository;
        this.trainingDirectionRepository = trainingDirectionRepository;
    }

    // Импорт заявлений с проверкой по ключевым полям.
    // Если при обработке строки возникает ошибка, она фиксируется, и после импорта выбрасывается исключение с подробностями.
    public void importStatements(MultipartFile file, LocalDate selectedDate) {
        StringBuilder errorMessages = new StringBuilder();
        List<Statement> allStatements = new ArrayList<>();
        Map<String, Integer> currentCounts = new HashMap<>(); // <-- перемещено сюда
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();
            // Пропускаем первые 9 строк (заголовки)
            for (int i = 0; i < 9 && rowIterator.hasNext(); i++) {
                rowIterator.next();
            }
            List<Statement> existingStatements = statementRepository.findByImportDate(selectedDate.toString());
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowNum = row.getRowNum();
                try {
                    // Получаем ключевые поля для проверки уникальности
                    String personalNumber = getCellValueAsString(row.getCell(31));
                    String instituteName = getCellValueAsString(row.getCell(57));
                    String trainingDirectionName = getCellValueAsString(row.getCell(59));
                    String importDateValue = selectedDate.toString();
                    String costReimbursementType = getCellValueAsString(row.getCell(49));
                    String targetedAdmission = getCellValueAsString(row.getCell(60));
                    String separateQuota = getCellValueAsString(row.getCell(66));
                    String specialQuota = getCellValueAsString(row.getCell(67));

                    // Найти или создать институт
                    Institute institute = instituteRepository.findByName(instituteName);
                    if (institute == null && !instituteName.isEmpty()) {
                        institute = new Institute();
                        institute.setName(instituteName);
                        institute.setLocation("Не указано"); // или другое значение по умолчанию
                        institute = instituteRepository.save(institute);
                    }

                    // Найти или создать направление и связать с институтом
                    if (institute != null && !trainingDirectionName.isEmpty()) {
                        TrainingDirection trainingDirection = trainingDirectionRepository.findByNameAndInstitute(trainingDirectionName, institute);
                        if (trainingDirection == null) {
                            trainingDirection = new TrainingDirection();
                            trainingDirection.setName(trainingDirectionName);
                            trainingDirection.setInstitute(institute);
                            trainingDirectionRepository.save(trainingDirection);
                        }
                    }

                    List<Statement> existing = repository.findAllByPersonalNumberAndImportDateAndInstituteAndTrainingDirectionAndCostReimbursementTypeAndTargetedAdmissionAndSeparateQuotaAndSpecialQuota(
                        personalNumber, importDateValue, instituteName, trainingDirectionName, costReimbursementType, targetedAdmission, separateQuota, specialQuota);

                    Statement statement;
                    if (!existing.isEmpty()) {
                        statement = existing.get(0);
                    } else {
                        statement = new Statement();
                        statement.setImportDate(importDateValue);
                        statement.setPersonalNumber(personalNumber);
                        statement.setInstitute(instituteName);
                        statement.setTrainingDirection(trainingDirectionName);
                        statement.setCostReimbursementType(costReimbursementType);
                        statement.setTargetedAdmission(targetedAdmission);
                        statement.setSeparateQuota(separateQuota);
                        statement.setSpecialQuota(specialQuota);
                    }
                    
                    // Получаем ФИО абитуриента из нужной колонки (например, пусть это колонка 11)
                    String fullName = getCellValueAsString(row.getCell(1));
                    statement.setFullName(fullName);
                    statement.setSpCode(getCellValueAsString(row.getCell(32)));
                    // 2) дата подачи заявления
                    statement.setSubmissionDate(getCellValueAsString(row.getCell(39)));
                    // 3) преимущественное право
                    statement.setPreferentialRight(getCellValueAsString(row.getCell(49)));
                    // 4) Вид возмещения затрат
                    statement.setCostReimbursementType(getCellValueAsString(row.getCell(50)));
                    // 5) вид приёма
                    statement.setAdmissionType(getCellValueAsString(row.getCell(52)));
                    // 6) дополнительный набор
                    statement.setAdditionalSet(getCellValueAsString(row.getCell(53)));
                    // 7) приоритет
                    statement.setPriority(getCellValueAsString(row.getCell(54)));
                    // 8) сквозной приоритет
                    statement.setOverallPriority(getCellValueAsString(row.getCell(55)));
                    // 11) набор ОП
                    statement.setOpSet(getCellValueAsString(row.getCell(59)));
                    // 12) Выбранные ОП
                    statement.setChosenOp(getCellValueAsString(row.getCell(60)));
                    // 13) Целевой приём
                    statement.setTargetedAdmission(getCellValueAsString(row.getCell(61)));
                    // 14) Без вступительных испытаний
                    statement.setNoEntranceExams(getCellValueAsString(row.getCell(66)));
                    // 15) Отдельная квота
                    statement.setSeparateQuota(getCellValueAsString(row.getCell(69)));
                    // 16) Особая квота
                    statement.setSpecialQuota(getCellValueAsString(row.getCell(70)));
                    // 17) Состояние выбранного конкурса
                    statement.setContestStatus(getCellValueAsString(row.getCell(72)));
                    // 18) Сдан оригинал
                    statement.setOriginalSubmitted(getCellValueAsString(row.getCell(74)));
                    // 19) Согласие на зачисление
                    statement.setEnrollmentConsent(getCellValueAsString(row.getCell(74)));
                    // 20) Дата согласия
                    statement.setConsentDate(getCellValueAsString(row.getCell(76)));
                    // 21) Итоговое согласие
                    statement.setFinalConsent(getCellValueAsString(row.getCell(80)));
                    // 22) Сумма баллов
                    statement.setTotalScore(getCellValueAsString(row.getCell(82)));
                    // 23) Результаты вступительных испытаний
                    statement.setExamResults(getCellValueAsString(row.getCell(83)));
                    // 24) р.я
                    statement.setRy(getCellValueAsString(row.getCell(84)));
                    // 25) фр.я
                    statement.setFrya(getCellValueAsString(row.getCell(85)));
                    // 26) общ
                    statement.setCommon(getCellValueAsString(row.getCell(86)));
                    // 27) лит
                    statement.setLiterature(getCellValueAsString(row.getCell(87)));
                    // 28) инф
                    statement.setInformatics(getCellValueAsString(row.getCell(88)));
                    // 29) м.проф
                    statement.setMpProfile(getCellValueAsString(row.getCell(89)));
                    // 30) эл.м.проф
                    statement.setElmpProfile(getCellValueAsString(row.getCell(90)));
                    // 31) ос.ин.р.проф
                    statement.setOsInRpProfile(getCellValueAsString(row.getCell(91)));
                    // 32) инф.проф
                    statement.setInformaticsProfile(getCellValueAsString(row.getCell(92)));
                    // 33) мат
                    statement.setMath(getCellValueAsString(row.getCell(93)));
                    // 34) био.проф.
                    statement.setBioProfile(getCellValueAsString(row.getCell(94)));
                    // 35) лит.проф.
                    statement.setLitProfile(getCellValueAsString(row.getCell(95)));
                    // 36) ист.проф.
                    statement.setHistoryProfile(getCellValueAsString(row.getCell(96)));
                    // 37) общ.проф.
                    statement.setCommonProfile(getCellValueAsString(row.getCell(97)));
                    // 38) мат.во
                    statement.setMatVo(getCellValueAsString(row.getCell(98)));
                    // 39) физ.во
                    statement.setPhysVo(getCellValueAsString(row.getCell(99)));
                    // 40) общ.во
                    statement.setCommonVo(getCellValueAsString(row.getCell(100)));
                    // 41) ист.во
                    statement.setHistoryVo(getCellValueAsString(row.getCell(1016)));
                    // 42) инф.во
                    statement.setInformaticsVo(getCellValueAsString(row.getCell(102)));
                    // 43) англ.во
                    statement.setEnglishVo(getCellValueAsString(row.getCell(103)));
                    // 44) нем.во
                    statement.setGermanVo(getCellValueAsString(row.getCell(104)));
                    // 45) лит.во
                    statement.setLitVo(getCellValueAsString(row.getCell(105)));
                    // 46) био.во
                    statement.setBioVo(getCellValueAsString(row.getCell(106)));
                    // 47) хим.во
                    statement.setChemVo(getCellValueAsString(row.getCell(107)));
                    // 48) Творч.исп.
                    statement.setCreativeExam(getCellValueAsString(row.getCell(108)));
                    // 49) физ
                    statement.setPhys(getCellValueAsString(row.getCell(109)));
                    // 50) хим
                    statement.setChem(getCellValueAsString(row.getCell(110)));
                    // 51) био
                    statement.setBio(getCellValueAsString(row.getCell(111)));
                    // 52) ист
                    statement.setHistory(getCellValueAsString(row.getCell(112)));
                    // 53) ан.я
                    statement.setAnya(getCellValueAsString(row.getCell(113)));
                    // 54) нем.я
                    statement.setNemya(getCellValueAsString(row.getCell(114)));
                    // 56) способ подачи документов
                    statement.setDocumentSubmissionMethod(getCellValueAsString(row.getCell(119)));
                    // 57) способ возрата документов
                    statement.setDocumentReturnMethod(getCellValueAsString(row.getCell(120)));
                    // 58) олимпиады
                    statement.setOlympiads(getCellValueAsString(row.getCell(121)));
                    // 59) результаты ЕГЭ
                    statement.setEgeResults(getCellValueAsString(row.getCell(122)));
                    // 60) Ря
                    statement.setRy2(getCellValueAsString(row.getCell(123)));
                    // 61) М
                    statement.setM(getCellValueAsString(row.getCell(124)));
                    // 62) Ф
                    statement.setF(getCellValueAsString(row.getCell(125)));
                    // 63) Х
                    statement.setH(getCellValueAsString(row.getCell(126)));
                    // 64) Б
                    statement.setB(getCellValueAsString(row.getCell(127)));
                    // 65) И
                    statement.setI(getCellValueAsString(row.getCell(128)));
                    // 66) Г
                    statement.setG(getCellValueAsString(row.getCell(129)));
                    // 67) Ая
                    statement.setAya(getCellValueAsString(row.getCell(130)));
                    // 68) Ня
                    statement.setNya(getCellValueAsString(row.getCell(131)));
                    // 69) Фя
                    statement.setFya(getCellValueAsString(row.getCell(132)));
                    // 70) О
                    statement.setO(getCellValueAsString(row.getCell(133)));
                    // 71) Л
                    statement.setL(getCellValueAsString(row.getCell(134)));
                    // 72) Ия
                    statement.setIya(getCellValueAsString(row.getCell(135)));
                    // 73) ИКТ
                    statement.setIkt(getCellValueAsString(row.getCell(136)));
                    // 74) Кя
                    statement.setKya(getCellValueAsString(row.getCell(137)));
                    // 75) ИТя
                    statement.setIty(getCellValueAsString(row.getCell(138)));
                    // 76) Статус результатов ЕГЭ
                    // statement.setEgeStatus(getCellValueAsString(row.getCell(139)));
                    // 78) Баллы за индивидуальные достижения
                    // statement.setIndividualAchievementScore(getCellValueAsString(row.getCell(140)));
                    // 79) Сумма баллов за индивидуальные достижения
                    statement.setIndividualAchievementTotal(getCellValueAsString(row.getCell(141)));
                    // 80) Баллы за индивидуальные достижения, учитываемые как преимущество
                    // statement.setIndividualAchievementAdvantageScore(getCellValueAsString(row.getCell(142)));
                    // 81) Сумма баллов за индивидуальные достижения, учитываемые как преимущество
                    statement.setIndividualAchievementAdvantageTotal(getCellValueAsString(row.getCell(143)));
                    
                    // Сохраняем (создаем или обновляем) запись
                    repository.saveAndFlush(statement);
                    allStatements.add(statement);

                    if (!instituteName.isEmpty() && !trainingDirectionName.isEmpty()) {
                        String key = instituteName + "|" + trainingDirectionName;
                        currentCounts.put(key, currentCounts.getOrDefault(key, 0) + 1);
                    }
                } catch (Exception ex) {
                    errorMessages.append("Ошибка обработки строки ")
                        .append(rowNum)
                        .append(" (Личный номер=")
                        .append(getCellValueAsString(row.getCell(10)))
                        .append(", институт=")
                        .append(getCellValueAsString(row.getCell(57)))
                        .append(", направление=")
                        .append(getCellValueAsString(row.getCell(58)))
                        .append("): ")
                        .append(ex.getMessage())
                        .append("\n");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения файла: " + e.getMessage(), e);
        }
        if (errorMessages.length() > 0) {
            // Если были ошибки при обработке отдельных строк - выбрасываем исключение с подробностями
            throw new RuntimeException("Ошибки при импорте заявлений:\n" + errorMessages.toString());
        }
        // После импорта всех заявлений считаем новые и забранные
        String previousDate = statementRepository.findPreviousDate(selectedDate.toString());
        Map<String, Integer> previousCounts = new HashMap<>();
        if (previousDate != null) {
            List<Statement> prevStatements = statementRepository.findByImportDate(previousDate);
            for (Statement s : prevStatements) {
                String key = s.getInstitute() + "|" + s.getTrainingDirection();
                previousCounts.put(key, previousCounts.getOrDefault(key, 0) + 1);
            }
        }
        for (String key : currentCounts.keySet()) {
            int current = currentCounts.getOrDefault(key, 0);
            int prev = previousCounts.getOrDefault(key, 0);
            int newStatements = Math.max(current - prev, 0);
            int withdrawnStatements = Math.max(prev - current, 0);
            String[] parts = key.split("\\|");
            String instituteName = parts[0];
            String directionName = parts[1];
            DirectionDailyStats stats = directionDailyStatsRepository.findByInstituteNameAndDirectionNameAndImportDate(instituteName, directionName, selectedDate);
            if (stats == null) stats = new DirectionDailyStats();
            stats.setInstituteName(instituteName);
            stats.setDirectionName(directionName);
            stats.setImportDate(selectedDate.toString());
            stats.setNewStatements(newStatements);
            stats.setWithdrawnStatements(withdrawnStatements);
            directionDailyStatsRepository.save(stats);
        }
        // После чтения всех заявлений в List<Statement> statements
        Map<String, List<Statement>> byApplicant = allStatements.stream()
            .collect(Collectors.groupingBy(s -> s.getPersonalNumber())); // или getFio(), если нет id

        for (List<Statement> applicantStatements : byApplicant.values()) {
            Map<Integer, String> priorityToDirection = applicantStatements.stream()
                .filter(s -> s.getPriority() != null && !s.getPriority().isEmpty())
                .collect(Collectors.groupingBy(
                    s -> Integer.parseInt(s.getPriority()),
                    Collectors.mapping(Statement::getTrainingDirection, Collectors.toList())
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().stream()
                        .filter(dir -> applicantStatements.stream()
                            .anyMatch(s -> s.getTrainingDirection().equals(dir) && "Общий конкурс".equals(s.getAdmissionType()))
                        )
                        .findFirst()
                        .orElse(e.getValue().get(0)) // если нет с "Общий конкурс", берём первое
                ));
            for (Statement s : applicantStatements) {
                s.setPriority1Direction(priorityToDirection.get(1));
                s.setPriority2Direction(priorityToDirection.get(2));
                s.setPriority3Direction(priorityToDirection.get(3));
                s.setPriority4Direction(priorityToDirection.get(4));
                s.setPriority5Direction(priorityToDirection.get(5));
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
    
    public List<Statement> getStatementsByPersonalNumber(String personalNumber) {
        return repository.findByPersonalNumber(personalNumber);
    }

    public List<Statement> getStatementsForDirection(String directionName, boolean onlyPriorityOne) {
        String latestDate = repository.findMaxImportDate(); // реализуйте этот метод, чтобы получить последнюю дату
        if (onlyPriorityOne) {
            return repository.findByTrainingDirectionAndImportDateAndPriorityOrderByTotalScoreDesc(directionName, latestDate, "1");
        } else {
            return repository.findByTrainingDirectionAndImportDateOrderByTotalScoreDesc(directionName, latestDate);
        }
    }

    public Map<String, Object> getStatementsWithPrevious(String directionName, boolean onlyPriorityOne, String date) {
        // Получаем текущие заявления за выбранную дату
        List<StatementPublicDto> current = statementRepository.findByDirectionAndDate(directionName, date, onlyPriorityOne).stream()
                                                                                                                .map(statementMapper::toPublicDto)
                                                                                                                .collect(Collectors.toList());

        // Получаем предыдущие заявления (например, за предыдущую дату)
        String previousDate = statementRepository.findPreviousDate(date);
        List<Statement> previous = previousDate != null
            ? statementRepository.findByDirectionAndDate(directionName, previousDate, onlyPriorityOne)
            : Collections.emptyList();

        List<StatementPublicDto> previousPublic = previous.stream()
                                                    .map(statementMapper::toPublicDto)
                                                    .collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("current", current);
        result.put("previous", previousPublic);
        return result;
    }

    public DirectionStatementsResponse getDirectionStatementsResponse(String instituteName, String directionName, boolean onlyPriorityOne, String date) {
        // Получаем текущие заявления за выбранную дату
        List<Statement> current = statementRepository.findByDirectionAndDate(directionName, date, onlyPriorityOne);
        // Получаем предыдущие заявления (например, за предыдущую дату)
        String previousDate = statementRepository.findPreviousDate(date);
        List<Statement> previous = previousDate != null
            ? statementRepository.findByDirectionAndDate(directionName, previousDate, onlyPriorityOne)
            : Collections.emptyList();
        // Получаем статистику
        int newStatements = 0;
        int withdrawnStatements = 0;
        if (date != null) {
            DirectionDailyStats stats = directionDailyStatsRepository.findByInstituteNameAndDirectionNameAndImportDate(instituteName, directionName, LocalDate.parse(date));
            if (stats != null) {
                newStatements = stats.getNewStatements();
                withdrawnStatements = stats.getWithdrawnStatements();
            }
        }
        return new DirectionStatementsResponse(current, previous, newStatements, withdrawnStatements);
    }

    public List<String> getAvailableDates() {
        // Предполагается, что в таблице Statement есть поле типа LocalDate или Date, например, "createdDate"
        // Получаем уникальные даты из базы, сортируем по возрастанию
        return statementRepository.findDistinctDates();
    }
}