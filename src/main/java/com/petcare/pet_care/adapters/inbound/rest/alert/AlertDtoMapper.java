package com.petcare.pet_care.adapters.inbound.rest.alert;

import com.petcare.pet_care.domain.alert.Alert;
import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertRequestDTO;
import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertResponseDTO;
import com.petcare.pet_care.domain.enums.AlertStatus;
import com.petcare.pet_care.domain.pet.Pet;
import org.springframework.stereotype.Component;

@Component
public class AlertDtoMapper {

    public Alert toDomain(AlertRequestDTO request) {
        Alert alert = new Alert();
        alert.setTypeAlert(request.getTypeAlert());
        alert.setDescription(request.getDescription());
        alert.setAlertGravity(request.getAlertGravity());
        alert.setAlertStatus(AlertStatus.PENDENTE);

        Pet pet = new Pet();
        pet.setId(request.getPetId());
        alert.setPet(pet);

        return alert;
    }

    public AlertResponseDTO toResponse(Alert alert) {
        AlertResponseDTO response = new AlertResponseDTO();
        response.setId(alert.getId());
        response.setTypeAlert(alert.getTypeAlert());
        response.setDescription(alert.getDescription());
        response.setDateAlert(alert.getDateAlert());
        response.setAlertGravity(alert.getAlertGravity());
        response.setAlertStatus(alert.getAlertStatus());

        if (alert.getPet() != null) {
            response.setPetId(alert.getPet().getId());
        }

        return response;
    }
}
