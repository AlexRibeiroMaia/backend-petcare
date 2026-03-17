package com.petcare.pet_care.adapters.outbound.repositories;

import com.petcare.pet_care.adapters.inbound.rest.pet.PetDtoMapper;
import com.petcare.pet_care.adapters.outbound.entities.JpaPetEntity;
import com.petcare.pet_care.adapters.outbound.persistence.pet.PetMapper;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository {


    private final JpaPetRepository jpaPetRepository;
    private final PetMapper petMapper;


    @Override
    public Pet save(Pet pet) {
        JpaPetEntity jpaPetEntity = jpaPetRepository.save(petMapper.toJpaEntity(pet));
        return petMapper.toDomain(jpaPetEntity);
    }

    @Override
    public Optional<Pet> findById(UUID id) {
        return jpaPetRepository.findById(id)
                .map(petMapper::toDomain);
    }

    @Override
    public List<Pet> findAll() {
        List<JpaPetEntity> entities = jpaPetRepository.findAll();
        return entities.stream()
                .map(petMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pet> findByTutorId(UUID tutorId) {
        return jpaPetRepository.findByTutorId(tutorId).stream()
                .map(petMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pet> findByEspecie(String especie) {
        return jpaPetRepository.findByEspecie(especie).stream()
                .map(petMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Pet> findByIdAndTutorId(UUID id, UUID tutorId) {
        return jpaPetRepository.findByIdAndTutorId(id, tutorId)
                .map(petMapper::toDomain);
    }

    @Override
    public void delete(Pet pet) {
        jpaPetRepository.findById(pet.getId())
                .ifPresent(jpaPetRepository::delete);
    }
}
