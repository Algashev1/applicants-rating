package com.example.backend.mapper;

import com.example.backend.model.Abiturient;
import com.example.backend.dto.AbiturientPublicDto;
import org.springframework.stereotype.Component;

@Component
public class AbiturientMapper {

    public AbiturientPublicDto toPublicDto(Abiturient abiturient) {
        return new AbiturientPublicDto(
            abiturient.getPersonalNumber(),
            abiturient.getSpCode(),
            abiturient.getGender(),
            abiturient.getBenefits(),
            abiturient.getForeignCitizenship()
        );
    }
}
