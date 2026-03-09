package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.domain.alert.AlertRequestDTO;
import com.petcare.pet_care.domain.alert.AlertResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AlertUseCases {
    AlertResponseDTO createAlert(AlertRequestDTO alert);

    AlertResponseDTO resolveAlert(Long id);

    List<AlertResponseDTO> findPendingAlertsByPetId(UUID petId);

    List<AlertResponseDTO> findAlertsByPetId(UUID petId);

    List<AlertResponseDTO> findAlertsByTutorId(UUID tutorId);

    long countPendingAlerts();

    List<AlertResponseDTO> findRecentAlerts();

}
