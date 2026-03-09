package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaAlertEntity;
import com.petcare.pet_care.domain.alert.Alert;
import com.petcare.pet_care.domain.alert.AlertRepository;
import com.petcare.pet_care.domain.enums.AlertStatus;
import com.petcare.pet_care.utils.mappers.AlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AlertRepositoryImpl implements AlertRepository {

    private final JpaAlertRepository jpaAlertRepository;
    private final AlertMapper alertMapper;

    @Override
    public Alert save(Alert alert) {
        JpaAlertEntity savedEntity = jpaAlertRepository.save(alertMapper.toJpaEntity(alert));
        return alertMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Alert> findById(Long id) {
        return jpaAlertRepository.findById(id)
                .map(alertMapper::toDomain);
    }

    @Override
    public List<Alert> findPendingAlertsByPetId(UUID petId) {
        return jpaAlertRepository.findPendingAlertByPetId(petId).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public List<Alert> findAlertsByPetId(UUID petId) {
        return jpaAlertRepository.findByPet_IdOrderByDateAlertDesc(petId).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public List<Alert> findAlertsByTutorId(UUID tutorId) {
        return jpaAlertRepository.findAlertasByTutorId(tutorId).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public long countPendingAlerts() {
        return jpaAlertRepository.countPendingAlert();
    }

    @Override
    public List<Alert> findAlertsByPeriod(LocalDateTime start, LocalDateTime end) {
        return jpaAlertRepository.findAlertPerTime(start, end).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public List<Alert> findByPet_IdOrderByDateAlertDesc(UUID petId) {
        return jpaAlertRepository.findByPet_IdOrderByDateAlertDesc(petId).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public List<Alert> findByAlertStatus(AlertStatus alertStatus) {
        return jpaAlertRepository.findByAlertStatus(alertStatus).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public List<Alert> findByPetIdAndAlertStatus(UUID petId, AlertStatus alertStatus) {
        return jpaAlertRepository.findByPetIdAndAlertStatus(petId, alertStatus).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public List<Alert> findAlertPerTime(LocalDateTime start, LocalDateTime end) {
        return jpaAlertRepository.findAlertPerTime(start, end).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public long countByAlertStatus(AlertStatus alertStatus) {
        return jpaAlertRepository.countByAlertStatus(alertStatus);
    }

    @Override
    public List<Alert> findAlertasByTutorId(UUID tutorId) {
        return jpaAlertRepository.findAlertasByTutorId(tutorId).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public List<Alert> findPendingAlertByPetId(UUID petId) {
        return jpaAlertRepository.findPendingAlertByPetId(petId).stream()
                .map(alertMapper::toDomain)
                .toList();
    }

    @Override
    public long countPendingAlert() {
        return jpaAlertRepository.countPendingAlert();
    }

    @Override
    public List<Alert> findAllAtivos() {
        return jpaAlertRepository.findAllAtivos().stream()
                .map(alertMapper::toDomain)
                .toList();
    }
}
