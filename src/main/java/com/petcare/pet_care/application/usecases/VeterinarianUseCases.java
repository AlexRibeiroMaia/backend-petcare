package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianResponseDto;

import java.util.List;
import java.util.UUID;

public interface VeterinarianUseCases {
    VeterinarianResponseDto create(VeterinarianRequestDto dto);

    VeterinarianResponseDto findById(UUID id);

    VeterinarianResponseDto findByCrmv(String crmv);

    VeterinarianResponseDto findByEmail(String email);

    List<VeterinarianResponseDto> findBySpecialty(String specialty);

    List<VeterinarianResponseDto> findByName(String name);

    List<VeterinarianResponseDto> findAll();

    VeterinarianResponseDto update(UUID id, VeterinarianRequestDto dto);

    void delete(UUID id);
}
