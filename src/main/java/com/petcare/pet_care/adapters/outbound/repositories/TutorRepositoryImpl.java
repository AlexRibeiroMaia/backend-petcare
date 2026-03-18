package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaTutorEntity;
import com.petcare.pet_care.adapters.outbound.persistence.tutor.TutorMapper;
import com.petcare.pet_care.domain.tutor.Tutor;
import com.petcare.pet_care.domain.tutor.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TutorRepositoryImpl implements TutorRepository {

    private final JpaTutorRepository jpaTutorRepository;
    private final TutorMapper tutorMapper;

    @Override
    public Tutor save(Tutor tutor) {
        JpaTutorEntity saved = jpaTutorRepository.save(tutorMapper.toJpaEntity(tutor));
        return tutorMapper.toDomain(saved);
    }

    @Override
    public Optional<Tutor> findById(UUID id) {
        return jpaTutorRepository.findById(id)
                .map(tutorMapper::toDomain);
    }

    @Override
    public Optional<Tutor> findByEmail(String email) {
        return jpaTutorRepository.findByEmail(email)
                .map(tutorMapper::toDomain);
    }

    @Override
    public Optional<Tutor> findByCpf(String cpf) {
        return jpaTutorRepository.findByCpf(cpf)
                .map(tutorMapper::toDomain);
    }

    @Override
    public Optional<Tutor> findByPhone(String phone) {
        return jpaTutorRepository.findByPhone(phone)
                .map(tutorMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaTutorRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaTutorRepository.existsByEmail(email);
    }

    @Override
    public List<Tutor> findByAdress(String adress) {
        return jpaTutorRepository.findByAddress(adress)
                .stream()
                .map(tutorMapper::toDomain)
                .toList();
    }

    @Override
    public List<Tutor> findAll() {
        return jpaTutorRepository.findAll().stream()
                .map(tutorMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaTutorRepository.deleteById(id);
    }

    @Override
    public List<Tutor> findByPetId(UUID petId) {
        return jpaTutorRepository.findByPetId(petId).stream()
                .map(tutorMapper::toDomain)
                .toList();
    }
}
