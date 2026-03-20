package com.petcare.pet_care.adapters.inbound.dtos.consultationDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationMedicalUpdateDto {
    private String diagnosis;
    private String prescription;
    private String observation;
}
