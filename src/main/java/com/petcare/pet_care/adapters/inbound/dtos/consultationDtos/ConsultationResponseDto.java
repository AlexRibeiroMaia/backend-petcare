package com.petcare.pet_care.adapters.inbound.dtos.consultationDtos;

import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationResponseDto {
    private Long id;
    private LocalDate date;
    private String diagnosis;
    private String prescription;
    private String observation;
    private PetResponseDto pet;
    private UUID veterinarianId;
}
