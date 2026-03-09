package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaAlertEntity;
import com.petcare.pet_care.domain.enums.AlertGravity;
import com.petcare.pet_care.domain.enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaAlertRepository extends JpaRepository<JpaAlertEntity, Long> {
    List<JpaAlertEntity> findByPet_IdOrderByDateAlertDesc(UUID petId);

    List<JpaAlertEntity> findByAlertStatus(AlertStatus status);

    List<JpaAlertEntity> findByAlertGravity(AlertGravity gravity);

    @Query("select a from JpaAlertEntity a where a.pet.id = :petId and a.alertStatus = :status")
    List<JpaAlertEntity> findByPetIdAndAlertStatus(
            @Param("petId") UUID petId,
            @Param("status") AlertStatus status);

    @Query("select a from JpaAlertEntity a where a.dateAlert between :start and :end")
    List<JpaAlertEntity> findAlertPerTime(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("select count(a) from JpaAlertEntity a where a.alertStatus = :status")
    long countByAlertStatus(@Param("status") AlertStatus status);

    @Query("select a from JpaAlertEntity a where a.pet.tutor.id = :tutorId")
    List<JpaAlertEntity> findAlertasByTutorId(@Param("tutorId") UUID tutorId);

    default List<JpaAlertEntity> findPendingAlertByPetId(UUID petId) {
        return findByPetIdAndAlertStatus(petId, AlertStatus.PENDING);
    }

    default long countPendingAlert() {
        return countByAlertStatus(AlertStatus.PENDING);
    }

    // Method to active alerts (Pendings alerts is Active)
    default List<JpaAlertEntity> findAllAtivos() {
        return findByAlertStatus(AlertStatus.PENDING);
    }

    UUID id(Long id);
}
