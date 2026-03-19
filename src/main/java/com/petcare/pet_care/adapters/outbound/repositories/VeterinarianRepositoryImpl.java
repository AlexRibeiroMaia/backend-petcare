package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.outbound.entities.JpaVeterinarianEntity;
import com.petcare.pet_care.adapters.outbound.persistence.veterinarian.VeterinarianMapper;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import com.petcare.pet_care.domain.veterinarian.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VeterinarianRepositoryImpl implements VeterinarianRepository {

    private final JpaVeterinarianRepository jpaVeterinarianRepository;
    private final VeterinarianMapper veterinarianMapper;

    @Override
    public Veterinarian save(Veterinarian veterinarian) {
        JpaVeterinarianEntity saved = jpaVeterinarianRepository.save(veterinarianMapper.toJpaEntity(veterinarian));
        return veterinarianMapper.toDomain(saved);
    }

    @Override
    public Optional<Veterinarian> findById(UUID id) {
        return jpaVeterinarianRepository.findById(id)
                .map(veterinarianMapper::toDomain);
    }

    @Override
    public Optional<Veterinarian> findByCrmv(String crmv) {
        return jpaVeterinarianRepository.findByCrmv(crmv)
                .map(veterinarianMapper::toDomain);
    }

    @Override
    public Optional<Veterinarian> findByEmail(String email) {
        return jpaVeterinarianRepository.findByEmail(email)
                .map(veterinarianMapper::toDomain);
    }

    @Override
    public List<Veterinarian> findBySpecialty(String specialty) {
        return jpaVeterinarianRepository.findBySpecialty(specialty).stream()
                .map(veterinarianMapper::toDomain)
                .toList();
    }

    @Override
    public List<Veterinarian> findByName(String name) {
        return jpaVeterinarianRepository.findByName(name).stream()
                .map(veterinarianMapper::toDomain)
                .toList();
    }

    @Override
    public List<Veterinarian> findAll() {
        return jpaVeterinarianRepository.findAll().stream()
                .map(veterinarianMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Veterinarian veterinarian) {
        jpaVeterinarianRepository.findById(veterinarian.getId())
                .ifPresent(jpaVeterinarianRepository::delete);
    }
}
