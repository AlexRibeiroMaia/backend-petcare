package com.petcare.pet_care.adapters.inbound.dtos.consultationDtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
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
public class ConsultationRequestDto {

    @NotNull(message = "Data e hora são obrigatorias")
    @Future(message = "Data da consulta deve ser no futuro")
    private LocalDate date;

    private String diagnosis;

    private String prescription;

    private String observation;

    @NotNull(message = "ID do pet é obrigatório")
    private UUID petId;

    @NotNull(message = "ID do veterinário é obrigatório")
    private UUID veterinarianId;
}
