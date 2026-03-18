package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaConsultationEntity;
import com.petcare.pet_care.adapters.outbound.persistence.consultation.ConsultationMapper;
import com.petcare.pet_care.domain.consultation.Consultation;
import com.petcare.pet_care.domain.consultation.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ConsultationRepositoryImpl implements ConsultationRepository {

    private final JpaConsultationRepository jpaConsultationRepository;
    private final ConsultationMapper consultationMapper;

    @Override
    public Consultation save(Consultation consultation) {
        JpaConsultationEntity saved = jpaConsultationRepository.save(consultationMapper.toJpaEntity(consultation));
        return consultationMapper.toDomain(saved);
    }

    @Override
    public Optional<Consultation> findById(Long id) {
        return jpaConsultationRepository.findById(id)
                .map(consultationMapper::toDomain);
    }

    @Override
    public List<Consultation> findAll() {
        return jpaConsultationRepository.findAll().stream()
                .map(consultationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Consultation> findByTutorId(UUID tutorId) {
        return jpaConsultationRepository.findConsultasByTutorId(tutorId).stream()
                .map(consultationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Consultation> findUpcomingByVeterinarianId(UUID veterinarianId) {
        return jpaConsultationRepository.findUpcomingConsultationsByVeterinarianId(veterinarianId, LocalDate.now())
                .stream()
                .map(consultationMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Consultation consultation) {
        jpaConsultationRepository.findById(consultation.getId())
                .ifPresent(jpaConsultationRepository::delete);
    }
}
