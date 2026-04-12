package com.petcare.pet_care.application.service.consultationService;

import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationMedicalUpdateDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationResponseDto;
import com.petcare.pet_care.adapters.inbound.dtos.emailDtos.ConsultationCreatedEmailQueueDto;
import com.petcare.pet_care.adapters.inbound.rest.consultation.ConsultationDtoMapper;
import com.petcare.pet_care.adapters.outbound.messaging.ConsultationCreatedEmailProducer;
import com.petcare.pet_care.application.exceptions.BadRequestException;
import com.petcare.pet_care.application.exceptions.ForbiddenException;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.ConsultationUseCases;
import com.petcare.pet_care.domain.consultation.Consultation;
import com.petcare.pet_care.domain.consultation.ConsultationRepository;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.pet.PetRepository;
import com.petcare.pet_care.domain.user.UserRole;
import com.petcare.pet_care.infra.security.AuthenticatedUser;
import com.petcare.pet_care.infra.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationUseCases {

    private final ConsultationRepository consultationRepository;
    private final ConsultationDtoMapper consultationDtoMapper;
    private final PetRepository petRepository;
    private final SecurityUtil securityUtil;
    private final ConsultationCreatedEmailProducer consultationCreatedEmailProducer;

    @Override
    public ConsultationResponseDto create(ConsultationRequestDto dto) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);

        if (dto.getDiagnosis() != null || dto.getPrescription() != null || dto.getObservation() != null) {
            throw new BadRequestException("Tutor cannot set diagnosis, prescription or observation during creation");
        }

        Pet pet = petRepository.findByIdAndTutorId(dto.getPetId(), user.getTutorId())
                .orElseThrow(() -> new ForbiddenException("Pet does not belong to the authenticated tutor"));

        Consultation consultation = consultationDtoMapper.toDomain(dto);
        consultation.setDiagnosis(null);
        consultation.setPrescription(null);
        consultation.setObservation(null);

        Consultation saved = consultationRepository.save(consultation);

        ConsultationCreatedEmailQueueDto queueDto = ConsultationCreatedEmailQueueDto.builder()
                .consultationId(saved.getId())
                .consultationDate(saved.getDate())
                .tutorId(user.getTutorId())
                .tutorEmail(user.getEmail())
                .petId(pet.getId())
                .petName(pet.getName())
                .veterinarianId(saved.getVeterinarian() != null ? saved.getVeterinarian().getId() : dto.getVeterinarianId())
                .build();

        consultationCreatedEmailProducer.publish(queueDto);

        return consultationDtoMapper.toResponseDto(saved);
    }

    @Override
    public ConsultationResponseDto findById(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        AuthenticatedUser user = securityUtil.getCurrentUser();
        if (user.getRole() == UserRole.TUTOR) {
            if (consultation.getPet() == null || consultation.getPet().getTutor() == null
                    || !consultation.getPet().getTutor().equals(user.getTutorId())) {
                throw new ForbiddenException("Access denied");
            }
        } else if (user.getRole() == UserRole.VETERINARIAN) {
            if (consultation.getVeterinarian() == null || consultation.getVeterinarian().getId() == null
                    || !consultation.getVeterinarian().getId().equals(user.getVeterinarianId())) {
                throw new ForbiddenException("Access denied");
            }
        } else if (user.getRole() != UserRole.ADMIN) {
            throw new ForbiddenException("Access denied");
        }

        return consultationDtoMapper.toResponseDto(consultation);
    }

    @Override
    public List<ConsultationResponseDto> findAll() {
        securityUtil.requireRole(UserRole.ADMIN);
        return consultationRepository.findAll().stream()
                .map(consultationDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<ConsultationResponseDto> findUpcomingByVeterinarianId(UUID veterinarianId) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.VETERINARIAN);
        if (user.getVeterinarianId() == null || !user.getVeterinarianId().equals(veterinarianId)) {
            throw new ForbiddenException("Access denied");
        }
        return consultationRepository.findUpcomingByVeterinarianId(veterinarianId).stream()
                .map(consultationDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public ConsultationResponseDto update(Long id, ConsultationMedicalUpdateDto dto) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.VETERINARIAN);
        Consultation existing = consultationRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        if (dto.getDiagnosis() == null || dto.getDiagnosis().isBlank()
                || dto.getPrescription() == null || dto.getPrescription().isBlank()) {
            throw new BadRequestException("Diagnosis and prescription are required");
        }

        if (existing.getVeterinarian() == null || existing.getVeterinarian().getId() == null
                || !existing.getVeterinarian().getId().equals(user.getVeterinarianId())) {
            throw new ForbiddenException("Access denied");
        }

        existing.setDiagnosis(dto.getDiagnosis());
        existing.setPrescription(dto.getPrescription());
        existing.setObservation(dto.getObservation());

        Consultation updated = consultationRepository.save(existing);
        return consultationDtoMapper.toResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        securityUtil.requireRole(UserRole.ADMIN);
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        consultationRepository.delete(consultation);
    }
}
