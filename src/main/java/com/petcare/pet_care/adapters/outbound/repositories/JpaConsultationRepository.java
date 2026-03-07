package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaConsultationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaConsultationRepository extends JpaRepository<JpaConsultationEntity, Long> {
    List<JpaConsultationEntity> findByPetIdOrderByDateDesc(UUID petId);


    @Query("select c from JpaConsultationEntity c where c.date between :start and :end")
    List<JpaConsultationEntity> findConsultasPorPeriodo(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Query("select c from JpaConsultationEntity c where c.pet.tutor.id = :tutorId")
    List<JpaConsultationEntity> findConsultasByTutorId(@Param("tutorId") UUID tutorId);

    @Query("select c from JpaConsultationEntity c where c.veterinarian.id = :veterinarioId and c.date > :dataAtual")
    List<JpaConsultationEntity> findUpcomingConsultationsByVeterinarianId(
            @Param("veterinarioId") UUID veterinarioId,
            @Param("dataAtual") LocalDate dataAtual);

    default List<JpaConsultationEntity> findAllAtivos() {
        return findConsultasPorPeriodo(LocalDate.now(), LocalDate.now().plusYears(1));
    }
}
