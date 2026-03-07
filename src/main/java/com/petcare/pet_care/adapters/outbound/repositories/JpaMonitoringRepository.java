package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaMonitoringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaMonitoringRepository extends JpaRepository<JpaMonitoringEntity, Long> {
    List<JpaMonitoringEntity> findByPet_IdOrderByDateDesc(UUID petId);

    List<JpaMonitoringEntity> findByDevice_IdOrderByDateDesc(Long deviceId);

    @Query("select m from JpaMonitoringEntity m where  m.pet.id = :petId and m.date between :start and :end")
    List<JpaMonitoringEntity> findByPetIdAndPeriodo(
            @Param("petId") UUID petId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("select m from JpaMonitoringEntity m where m.corporalTemperature > :tempLimite or m.heartHate > :freqLimite")
    List<JpaMonitoringEntity> findMetricasCriticas(
            @Param("tempLimite") Double tempLimite,
            @Param("freqLimite") Integer freqLimite);

    Optional<JpaMonitoringEntity> findFirstByPet_IdOrderByDateDesc(UUID petId);
}
