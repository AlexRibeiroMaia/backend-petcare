package com.petcare.pet_care.adapters.outbound.persistence.consultation;

import com.petcare.pet_care.adapters.outbound.entities.JpaConsultationEntity;
import com.petcare.pet_care.adapters.outbound.persistence.pet.PetMapper;
import com.petcare.pet_care.adapters.outbound.repositories.JpaPetRepository;
import com.petcare.pet_care.adapters.outbound.repositories.JpaVeterinarianRepository;
import com.petcare.pet_care.domain.consultation.Consultation;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultationMapper {

    private final JpaPetRepository jpaPetRepository;
    private final JpaVeterinarianRepository jpaVeterinarianRepository;
    private final PetMapper petMapper;

    public Consultation toDomain(JpaConsultationEntity entity) {
        if (entity == null) {
            return null;
        }

        Consultation consultation = new Consultation();
        consultation.setId(entity.getId());
        consultation.setDate(entity.getDate());
        consultation.setDiagnosis(entity.getDiagnosis());
        consultation.setPrescription(entity.getPrescription());
        consultation.setObservation(entity.getObservation());

        if (entity.getPet() != null) {
            Pet pet = petMapper.toDomain(entity.getPet());
            consultation.setPet(pet);
        }

        if (entity.getVeterinarian() != null) {
            Veterinarian veterinarian = new Veterinarian();
            veterinarian.setId(entity.getVeterinarian().getId());
            consultation.setVeterinarian(veterinarian);
        }

        return consultation;
    }

    public JpaConsultationEntity toJpaEntity(Consultation consultation) {
        if (consultation == null) {
            return null;
        }

        JpaConsultationEntity entity = new JpaConsultationEntity();
        entity.setId(consultation.getId());
        entity.setDate(consultation.getDate());
        entity.setDiagnosis(consultation.getDiagnosis());
        entity.setPrescription(consultation.getPrescription());
        entity.setObservation(consultation.getObservation());

        if (consultation.getPet() != null && consultation.getPet().getId() != null) {
            entity.setPet(jpaPetRepository.getReferenceById(consultation.getPet().getId()));
        }

        if (consultation.getVeterinarian() != null && consultation.getVeterinarian().getId() != null) {
            entity.setVeterinarian(
                    jpaVeterinarianRepository.getReferenceById(consultation.getVeterinarian().getId()));
        }

        return entity;
    }
}
