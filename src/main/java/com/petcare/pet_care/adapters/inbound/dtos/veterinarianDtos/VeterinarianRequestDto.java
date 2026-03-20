package com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos;

import com.petcare.pet_care.adapters.inbound.validation.CrmvNotation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarianRequestDto {

    @NotBlank(message = "Nome é obrigatorio")
    private String name;

    @NotBlank(message = "Especialidade é obrigatoria")
    private String specialty;

    @NotBlank(message = "Telefone é obrigatorio")
    @Size(max = 11, message = "Telefone deve ter no máximo 11 dígitos")
    private String phone;

    @NotBlank(message = "Email é obrigatorio")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "CRMV é obrigatorio")
    @CrmvNotation
    private String crmv;

    private String password;
}
