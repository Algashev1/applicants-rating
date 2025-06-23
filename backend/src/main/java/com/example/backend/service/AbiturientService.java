package com.example.backend.service;

import com.example.backend.model.Abiturient;
import com.example.backend.repository.AbiturientRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;

@Service
public class AbiturientService {

    private final AbiturientRepository repository;

    public AbiturientService(AbiturientRepository repository) {
        this.repository = repository;
    }

    public void importAbiturients(MultipartFile file) {
        System.out.println("Старт");
        // Увеличиваем максимальный размер байтового массива для записи
        IOUtils.setByteArrayMaxOverride(200_000_000); // значение можно корректировать под ваши нужды

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            // Получаем второй лист (индекс 1)
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();

            // Пропускаем первые 9 строк (индекс 0-8)
            for (int i = 0; i < 9 && rowIterator.hasNext(); i++) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Abiturient abiturient = new Abiturient();

                // Новое сопоставление:
                // 0 - ФИО, 1 - Пол, 2 - Дата рождения, 3 - личный номер,
                // 4 - Льготы, 5 - Контактный телефон, 6 - Домашний телефон,
                // 7 - Мобильный телефон, 8 - e-mail, 9 - Иностранное гражданство

                abiturient.setFullName(getCellString(row.getCell(1)));
                abiturient.setGender(getCellString(row.getCell(2)));

                // Cell birthDateCell = row.getCell(3);
                // if (birthDateCell != null && DateUtil.isCellDateFormatted(birthDateCell)) {
                //     Date date = birthDateCell.getDateCellValue();
                //     LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                //     abiturient.setBirthDate(localDate);
                // }

                abiturient.setPersonalNumber(getCellString(row.getCell(31)));
                abiturient.setBenefits(getCellString(row.getCell(13)));
                abiturient.setContactPhone(getCellString(row.getCell(22)));
                abiturient.setHomePhone(getCellString(row.getCell(23)));
                abiturient.setMobilePhone(getCellString(row.getCell(24)));
                abiturient.setEmail(getCellString(row.getCell(25)));
                String fc = getCellString(row.getCell(29));
                abiturient.setForeignCitizenship("yes".equalsIgnoreCase(fc) ||
                                                 "true".equalsIgnoreCase(fc));

                // Если запись по личному номеру существует, обновляем запись; иначе сохраняем новую
                repository.findByPersonalNumber(abiturient.getPersonalNumber()).ifPresentOrElse(existing -> {
                    existing.setFullName(abiturient.getFullName());
                    existing.setGender(abiturient.getGender());
                    existing.setBirthDate(abiturient.getBirthDate());
                    existing.setBenefits(abiturient.getBenefits());
                    existing.setContactPhone(abiturient.getContactPhone());
                    existing.setHomePhone(abiturient.getHomePhone());
                    existing.setMobilePhone(abiturient.getMobilePhone());
                    existing.setEmail(abiturient.getEmail());
                    existing.setForeignCitizenship(abiturient.getForeignCitizenship());
                    repository.save(existing);
                    System.out.println("Обновлён абитуриент (Личный номер: " + abiturient.getPersonalNumber() + ")");
                }, () -> {
                    repository.save(abiturient);
                    System.out.println("Добавлен новый абитуриент (Личный номер: " + abiturient.getPersonalNumber() + ")");
                });
            }
        } catch (Exception e) {
            System.err.println("Ошибка импорта абитуриентов: " + e.getMessage());
            throw new RuntimeException("Failed to import abiturients", e);
        }
    }

    private String getCellString(Cell cell) {
        if (cell == null) {
            System.out.println("Ячейка равна null");
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        // Получаем адрес ячейки для отладки
        String cellAddress = cell.getAddress().formatAsString();
        String value = formatter.formatCellValue(cell).trim();
        System.out.println("Чтение ячейки " + cellAddress + ": значение = \"" + value + "\"");
        return value;
    }
}