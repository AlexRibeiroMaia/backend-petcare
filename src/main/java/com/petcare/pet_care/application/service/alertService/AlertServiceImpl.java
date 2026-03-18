package com.petcare.pet_care.application.service.alertService;

import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertRequestDTO;
import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertResponseDTO;
import com.petcare.pet_care.adapters.inbound.rest.alert.AlertDtoMapper;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.AlertUseCases;
import com.petcare.pet_care.domain.alert.Alert;
import com.petcare.pet_care.domain.alert.AlertRepository;
import com.petcare.pet_care.domain.enums.AlertStatus;
import jakarta.persistence.NoResultException;
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
    public AlertResponseDTO createAlert(AlertRequestDTO alertRequest) {
        Alert alert = alertDtoMapper.toDomain(alertRequest);
        if (alert.getDateAlert() == null) {
            alert.setDateAlert(LocalDateTime.now());
        }
        if (alert.getAlertStatus() == null) {
            alert.setAlertStatus(AlertStatus.PENDENTE);
        }
        Alert saved = alertRepository.save(alert);
        return alertDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public AlertResponseDTO resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        alert.setAlertStatus(AlertStatus.RESOLVIDO);
        Alert saved = alertRepository.save(alert);
        return alertDtoMapper.toResponse(saved);
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
        if (petId == null) {
            throw new NoResultException("Pet id not found");
        }
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
