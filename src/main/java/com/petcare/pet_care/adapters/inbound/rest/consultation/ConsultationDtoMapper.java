package com.petcare.pet_care.adapters.inbound.rest.consultation;

import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.pet.PetDtoMapper;
import com.petcare.pet_care.domain.consultation.Consultation;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultationDtoMapper {

    private final PetDtoMapper petDtoMapper;

    public Consultation toDomain(ConsultationRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Consultation consultation = new Consultation();
        consultation.setDate(dto.getDate());
        consultation.setDiagnosis(dto.getDiagnosis());
        consultation.setPrescription(dto.getPrescription());
        consultation.setObservation(dto.getObservation());

        Pet pet = new Pet();
        pet.setId(dto.getPetId());
        consultation.setPet(pet);

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setId(dto.getVeterinarianId());
        consultation.setVeterinarian(veterinarian);

        return consultation;
    }

    public ConsultationResponseDto toResponseDto(Consultation consultation) {
        if (consultation == null) {
            return null;
        }

        return ConsultationResponseDto.builder()
                .id(consultation.getId())
                .date(consultation.getDate())
                .diagnosis(consultation.getDiagnosis())
                .prescription(consultation.getPrescription())
                .observation(consultation.getObservation())
                .pet(consultation.getPet() != null ? petDtoMapper.toResponseDto(consultation.getPet()) : null)
                .veterinarianId(consultation.getVeterinarian() != null ? consultation.getVeterinarian().getId() : null)
                .build();
    }
}
