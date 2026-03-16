package com.petcare.pet_care.adapters.inbound.dtos.petDtos;


import com.petcare.pet_care.domain.enums.Sex;
import com.petcare.pet_care.domain.tutor.Tutor;
import jakarta.validation.constraints.NotBlank;
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
public class PetRequestDto {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Espécie é obrigatória")
    private String especie;

    private String race;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate birthDate;

    private Double weight;

    @NotNull(message = "Sexo é obrigatório")
    private Sex sex;

    @NotNull(message = "ID do tutor é obrigatório")
    private UUID tutorId;

}
