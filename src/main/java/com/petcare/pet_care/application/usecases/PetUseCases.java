package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetResponseDto;

import java.util.List;
import java.util.UUID;

public interface PetUseCases {
    PetResponseDto create(PetRequestDto dto);

    PetResponseDto findById(UUID id);

    List<PetResponseDto> findAll();

    List<PetResponseDto> findByTutorId(UUID tutorId);

    List<PetResponseDto> findByEspecie(String especie);

    PetResponseDto findByIdAndTutorId(UUID id, UUID tutorId);

    long countByEspecie(String especie);

    PetResponseDto update(UUID id, PetRequestDto dto);

    void delete(UUID id);
}
