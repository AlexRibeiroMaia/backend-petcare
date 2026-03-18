package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorResponseDto;

import java.util.List;
import java.util.UUID;

public interface TutorUseCases {
    TutorResponseDto create(TutorRequestDto dto);

    TutorResponseDto findById(UUID id);

    TutorResponseDto findByEmail(String email);

    TutorResponseDto findByCpf(String cpf);

    TutorResponseDto findByPhone(String phone);

    List<TutorResponseDto> findAll();

    List<TutorResponseDto> findByPetId(UUID petId);

    TutorResponseDto update(UUID id, TutorRequestDto dto);

    void delete(UUID id);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);
}
