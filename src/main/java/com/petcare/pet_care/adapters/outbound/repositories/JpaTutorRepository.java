package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaTutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaTutorRepository extends JpaRepository<JpaTutorEntity, UUID> {
    Optional<JpaTutorEntity> findByEmail(String email);

    List<JpaTutorEntity> findByAddress(String address);
}
