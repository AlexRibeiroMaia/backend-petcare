package com.petcare.pet_care.application.usecases;

import com.petcare.pet_care.domain.alert.Alert;

import java.util.List;
import java.util.UUID;

public interface AlertUseCases {
    Alert createAlert(Alert alert);

    Alert resolveAlert(Long id);

    List<Alert> findPendingAlertsByPetId(UUID petId);

    List<Alert> findAlertsByPetId(UUID petId);

    List<Alert> findAlertsByTutorId(UUID tutorId);

    long countPendingAlerts();

    List<Alert> findRecentAlerts();

}
