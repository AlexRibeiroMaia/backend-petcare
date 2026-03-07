package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaDeviceEntity;
import com.petcare.pet_care.domain.enums.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDeviceRepository extends JpaRepository<JpaDeviceEntity, Long> {
    Optional<JpaDeviceEntity> findBySerialNumber(String serialNumber);

    List<JpaDeviceEntity> findByPet_Id(UUID petId);

    List<JpaDeviceEntity> findByStatus(DeviceStatus status);

    @Query("select d from JpaDeviceEntity d where d.pet.tutor.id = :tutorId")
    List<JpaDeviceEntity> findByTutorId(@Param("tutorId") UUID tutorId);

    @Query("select count(d) from JpaDeviceEntity d where d.status = :status")
    long countByStatus(@Param("status") DeviceStatus status);

    @Query("select d from JpaDeviceEntity d where d.pet.id = :petId and d.status = :status")
    Optional<JpaDeviceEntity> findByPetIdAndStatus(
            @Param("petId") UUID petId,
            @Param("status") DeviceStatus status);

    default long countActiveDevices() {
        return countByStatus(DeviceStatus.ACTIVE);
    }

    default Optional<JpaDeviceEntity> findDispositivoAtivoByPetId(UUID petId) {
        return findByPetIdAndStatus(petId, DeviceStatus.ACTIVE);
    }

    default List<JpaDeviceEntity> findAllAtivos() {
        return findByStatus(DeviceStatus.ACTIVE);
    }
}
