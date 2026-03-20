package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationMedicalUpdateDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationResponseDto;

import java.util.List;
import java.util.UUID;

public interface ConsultationUseCases {
    ConsultationResponseDto create(ConsultationRequestDto dto);

    ConsultationResponseDto findById(Long id);

    List<ConsultationResponseDto> findAll();

    List<ConsultationResponseDto> findUpcomingByVeterinarianId(UUID veterinarianId);

    ConsultationResponseDto update(Long id, ConsultationMedicalUpdateDto dto);

    void delete(Long id);
}
