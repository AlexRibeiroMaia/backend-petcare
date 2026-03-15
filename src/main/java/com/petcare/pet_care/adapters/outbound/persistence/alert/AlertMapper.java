package com.petcare.pet_care.adapters.outbound.persistence.alert;

import com.petcare.pet_care.adapters.outbound.entities.JpaAlertEntity;
import com.petcare.pet_care.adapters.outbound.entities.JpaPetEntity;
import com.petcare.pet_care.adapters.outbound.repositories.JpaPetRepository;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.tutor.Tutor;
import com.petcare.pet_care.domain.alert.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertMapper {

    private final JpaPetRepository jpaPetRepository;

    public Alert toDomain(JpaAlertEntity entity) {
        Alert alert = new Alert();
        alert.setId(entity.getId());
        alert.setTypeAlert(entity.getTypeAlert());
        alert.setDescription(entity.getDescription());
        alert.setDateAlert(entity.getDateAlert());
        alert.setAlertGravity(entity.getAlertGravity());
        alert.setAlertStatus(entity.getAlertStatus());

        if (entity.getPet() != null) {
            alert.setPet(toDomainPet(entity.getPet()));
        }

        return alert;
    }

    public JpaAlertEntity toJpaEntity(Alert alert) {
        JpaAlertEntity entity = new JpaAlertEntity();
        entity.setId(alert.getId());
        entity.setTypeAlert(alert.getTypeAlert());
        entity.setDescription(alert.getDescription());
        entity.setDateAlert(alert.getDateAlert());
        entity.setAlertGravity(alert.getAlertGravity());
        entity.setAlertStatus(alert.getAlertStatus());

        if (alert.getPet() != null && alert.getPet().getId() != null) {
            entity.setPet(jpaPetRepository.getReferenceById(alert.getPet().getId()));
        }

        return entity;
    }

    private Pet toDomainPet(JpaPetEntity entity) {
        Pet pet = new Pet();
        pet.setId(entity.getId());

        if (entity.getTutor() != null) {
            Tutor tutor = new Tutor();
            tutor.setId(entity.getTutor().getId());
            pet.setTutor(tutor.getId());
        }

        return pet;
    }
}
