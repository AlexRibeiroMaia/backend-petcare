package com.petcare.pet_care.domain.pet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository {
    Pet save(Pet pet);

    Optional<Pet> findById(UUID id);

    List<Pet> findAll();

    List<Pet> findByTutorId(UUID tutorId);

    List<Pet> findByEspecie(String especie);

    Optional<Pet> findByIdAndTutorId(UUID id, UUID tutorId);

    long countByEspecie(String especie);

    void delete(Pet pet);
}
