package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaPetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPetRepository extends JpaRepository<JpaPetEntity, UUID> {

    List<JpaPetEntity> findByTutorId(UUID tutorId);
    List<JpaPetEntity> findByEspecie(String especie);

    Optional<JpaPetEntity> findByIdAndTutorId(UUID id, UUID tutorId);

    List<JpaPetEntity> findByTutorEmail(@Param("email") String email);

    @Query("select count (p) from JpaPetEntity p where p.especie = :especie")
    long countByEspecie(String especie);

}
