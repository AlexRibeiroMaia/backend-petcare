package com.petcare.pet_care.application.service.consultationService;

import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.consultation.ConsultationDtoMapper;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.ConsultationUseCases;
import com.petcare.pet_care.domain.consultation.Consultation;
import com.petcare.pet_care.domain.consultation.ConsultationRepository;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationUseCases {

    private final ConsultationRepository consultationRepository;
    private final ConsultationDtoMapper consultationDtoMapper;

    @Override
    public ConsultationResponseDto create(ConsultationRequestDto dto) {
        Consultation consultation = consultationDtoMapper.toDomain(dto);
        Consultation saved = consultationRepository.save(consultation);
        return consultationDtoMapper.toResponseDto(saved);
    }

    @Override
    public ConsultationResponseDto findById(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return consultationDtoMapper.toResponseDto(consultation);
    }

    @Override
    public List<ConsultationResponseDto> findAll() {
        return consultationRepository.findAll().stream()
                .map(consultationDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<ConsultationResponseDto> findUpcomingByVeterinarianId(UUID veterinarianId) {
        return consultationRepository.findUpcomingByVeterinarianId(veterinarianId).stream()
                .map(consultationDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public ConsultationResponseDto update(Long id, ConsultationRequestDto dto) {
        Consultation existing = consultationRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        existing.setDate(dto.getDate());
        existing.setDiagnosis(dto.getDiagnosis());
        existing.setPrescription(dto.getPrescription());
        existing.setObservation(dto.getObservation());

        Pet pet = new Pet();
        pet.setId(dto.getPetId());
        existing.setPet(pet);

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setId(dto.getVeterinarianId());
        existing.setVeterinarian(veterinarian);

        Consultation updated = consultationRepository.save(existing);
        return consultationDtoMapper.toResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        consultationRepository.delete(consultation);
    }
}
