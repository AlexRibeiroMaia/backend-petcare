package com.petcare.pet_care.adapters.inbound.dtos.tutorDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String address;
    private LocalDateTime cadasterDate;
}
