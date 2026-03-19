package com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarianResponseDto {
    private UUID id;
    private String name;
    private String specialty;
    private String phone;
    private String email;
    private String crmv;
}
