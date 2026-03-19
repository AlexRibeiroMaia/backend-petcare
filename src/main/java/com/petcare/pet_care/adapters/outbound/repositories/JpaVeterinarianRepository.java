package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaVeterinarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaVeterinarianRepository extends JpaRepository<JpaVeterinarianEntity, UUID> {
    Optional<JpaVeterinarianEntity> findByCrmv(String crmv);

    Optional<JpaVeterinarianEntity> findByEmail(String email);

    Optional<JpaVeterinarianEntity> findByPhone(String phone);

    List<JpaVeterinarianEntity> findBySpecialty(String especialidade);

    @Query("SELECT v FROM JpaVeterinarianEntity v WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<JpaVeterinarianEntity> findByName(@Param("nome") String nome);
}
