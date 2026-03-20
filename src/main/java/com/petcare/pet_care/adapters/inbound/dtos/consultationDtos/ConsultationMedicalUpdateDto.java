package com.petcare.pet_care.adapters.inbound.dtos.consultationDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationMedicalUpdateDto {
    @NotBlank(message = "Diagnóstico é obrigatório")
    private String diagnosis;

    @NotBlank(message = "Prescrição é obrigatória")
    private String prescription;

    private String observation;
}
