package com.petcare.pet_care.adapters.inbound.dtos.petDtos;

import com.petcare.pet_care.domain.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {
    private UUID id;
    private String name;
    private String especie;
    private String race;
    private LocalDate birthDate;
    private Double weight;
    private Sex sex;
    private LocalDateTime cadasterDate;
    private UUID tutor;
}
