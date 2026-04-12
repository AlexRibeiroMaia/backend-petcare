package com.petcare.pet_care.adapters.inbound.dtos.emailDtos;

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
public class ConsultationCreatedEmailQueueDto {
    private Long consultationId;
    private LocalDate consultationDate;
    private UUID tutorId;
    private String tutorEmail;
    private UUID petId;
    private String petName;
    private UUID veterinarianId;
}
