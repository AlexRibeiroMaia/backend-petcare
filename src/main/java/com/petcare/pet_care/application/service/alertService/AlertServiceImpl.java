package com.petcare.pet_care.application.service.alertService;

import com.petcare.pet_care.application.usecases.AlertUseCases;
import com.petcare.pet_care.domain.alert.Alert;
import com.petcare.pet_care.domain.alert.AlertRepository;
import com.petcare.pet_care.domain.enums.AlertStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertUseCases {

    private final AlertRepository alertRepository;

    @Override
    @Transactional
    public Alert createAlert(Alert alert) {
        if (alert.getDateAlert() == null) {
            alert.setDateAlert(LocalDateTime.now());
        }
        if (alert.getAlertStatus() == null) {
            alert.setAlertStatus(AlertStatus.PENDENTE);
        }
        return alertRepository.save(alert);
    }

    @Override
    @Transactional
    public Alert resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setAlertStatus(AlertStatus.RESOLVIDO);
        return alertRepository.save(alert);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Alert> findPendingAlertsByPetId(UUID petId) {
        return alertRepository.findPendingAlertsByPetId(petId);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Alert> findAlertsByPetId(UUID petId) {
        return alertRepository.findAlertsByPetId(petId);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Alert> findAlertsByTutorId(UUID tutorId) {
        return alertRepository.findAlertsByTutorId(tutorId);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public long countPendingAlerts() {
        return alertRepository.countPendingAlerts();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Alert> findRecentAlerts() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = now.minusDays(1);

        return alertRepository.findAlertsByPeriod(oneDayAgo, now);
    }


}
