package com.petcare.pet_care.adapters.inbound.dtos.authDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String tokenType;
    private Long expiresAt;
}
