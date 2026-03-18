package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaTutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaTutorRepository extends JpaRepository<JpaTutorEntity, UUID> {
    Optional<JpaTutorEntity> findByEmail(String email);

    Optional<JpaTutorEntity> findByCpf(String cpf);

    Optional<JpaTutorEntity> findByPhone(String phone);

    boolean existsByEmail(String email);

    List<JpaTutorEntity> findByAddress(String address);

    @Query("select t from JpaTutorEntity t join t.pets p where p.id = :petId")
    List<JpaTutorEntity> findByPetId(@Param("petId") UUID petId);

}
