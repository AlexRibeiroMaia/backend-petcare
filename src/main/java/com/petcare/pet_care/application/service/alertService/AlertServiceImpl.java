package com.petcare.pet_care.application.service.alertservice;

import com.petcare.pet_care.application.usecases.AlertUseCases;
import com.petcare.pet_care.domain.alert.Alert;
import com.petcare.pet_care.domain.alert.AlertRepository;
import com.petcare.pet_care.domain.alert.AlertRequestDTO;
import com.petcare.pet_care.domain.alert.AlertResponseDTO;
import com.petcare.pet_care.domain.enums.AlertStatus;
import com.petcare.pet_care.utils.mappers.AlertDtoMapper;
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
    private final AlertDtoMapper alertDtoMapper;

    @Override
    @Transactional
    public AlertResponseDTO createAlert(AlertRequestDTO request) {
        Alert alert = alertDtoMapper.toDomain(request);
        alert.setDateAlert(LocalDateTime.now());

        return alertDtoMapper.toResponse(alertRepository.save(alert));
    }

    @Override
    @Transactional
    public AlertResponseDTO resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setAlertStatus(AlertStatus.RESOLVED);
        return alertDtoMapper.toResponse(alertRepository.save(alert));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<AlertResponseDTO> findPendingAlertsByPetId(UUID petId) {
        return alertRepository.findPendingAlertsByPetId(petId).stream()
                .map(alertDtoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<AlertResponseDTO> findAlertsByPetId(UUID petId) {
        return alertRepository.findAlertsByPetId(petId).stream()
                .map(alertDtoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<AlertResponseDTO> findAlertsByTutorId(UUID tutorId) {
        return alertRepository.findAlertsByTutorId(tutorId).stream()
                .map(alertDtoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public long countPendingAlerts() {
        return alertRepository.countPendingAlerts();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<AlertResponseDTO> findRecentAlerts() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = now.minusDays(1);

        return alertRepository.findAlertsByPeriod(oneDayAgo, now).stream()
                .map(alertDtoMapper::toResponse)
                .toList();
    }
}
