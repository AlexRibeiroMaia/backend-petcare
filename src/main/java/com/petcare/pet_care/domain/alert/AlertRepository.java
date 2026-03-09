package com.petcare.pet_care.domain.alert;

import com.petcare.pet_care.domain.enums.AlertStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlertRepository {
    Alert save(Alert alert);

    Optional<Alert> findById(Long id);

    List<Alert> findPendingAlertsByPetId(UUID petId);

    List<Alert> findAlertsByPetId(UUID petId);

    List<Alert> findAlertsByTutorId(UUID tutorId);

    long countPendingAlerts();

    List<Alert> findAlertsByPeriod(LocalDateTime start, LocalDateTime end);

    List<Alert> findByPet_IdOrderByDateAlertDesc(UUID petId);

    List<Alert> findByAlertStatus (AlertStatus alertStatus);

    List<Alert> findByPetIdAndAlertStatus (UUID petId, AlertStatus alertStatus);

    List<Alert> findAlertPerTime (LocalDateTime start, LocalDateTime end);

    long countByAlertStatus (AlertStatus alertStatus);

    List<Alert> findAlertasByTutorId (UUID tutorId);

    List<Alert> findPendingAlertByPetId (UUID petId);

    long countPendingAlert();

    List<Alert> findAllAtivos();

}
