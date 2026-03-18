package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertRequestDTO;
import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AlertUseCases {
    AlertResponseDTO createAlert(AlertRequestDTO alertRequest);

    AlertResponseDTO resolveAlert(Long id);

    List<AlertResponseDTO> findPendingAlertsByPetId(UUID petId);

    List<AlertResponseDTO> findAlertsByPetId(UUID petId);

    List<AlertResponseDTO> findAlertsByTutorId(UUID tutorId);

    long countPendingAlerts();

    List<AlertResponseDTO> findRecentAlerts();

}
