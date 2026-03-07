package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaAlertEntity;
import com.petcare.pet_care.domain.enums.AlertGravity;
import com.petcare.pet_care.domain.enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface JpaAlertRepository extends JpaRepository<JpaAlertEntity, String> {
    List<JpaAlertEntity> findByPetIdOrderByDataHoraDesc(String petId);

    List<JpaAlertEntity> findByStatus(AlertStatus status);

    List<JpaAlertEntity> findByGravity(AlertGravity gravidade);

    @Query("select a from JpaAlertEntity a where a.pet.id = :petId and a.alertStatus = 'PENDING'")
    List<JpaAlertEntity> findPendingAlertByPetId(@Param("petId") String petId);

    @Query("select a from JpaAlertEntity a where a.dateAlert between :start and :end")
    List<JpaAlertEntity> findAlertPerTime(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(a) FROM JpaAlertEntity a WHERE a.alertStatus = 'PENDING'")
    long countPendingAlert();

    @Query("SELECT a FROM JpaAlertEntity a WHERE a.pet.tutor.id = :tutorId")
    List<JpaAlertEntity> findAlertasByTutorId(@Param("tutorId") String tutorId);

    // Method to active alerts (Pendings alerts is Active)
    default List<JpaAlertEntity> findAllAtivos() {
        return findByStatus(AlertStatus.PENDING);
    }
}

