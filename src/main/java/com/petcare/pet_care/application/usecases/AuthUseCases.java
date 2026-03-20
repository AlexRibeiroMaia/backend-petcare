package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.adapters.inbound.dtos.authDtos.AuthRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.authDtos.AuthResponseDto;

public interface AuthUseCases {
    AuthResponseDto login(AuthRequestDto dto);
}
