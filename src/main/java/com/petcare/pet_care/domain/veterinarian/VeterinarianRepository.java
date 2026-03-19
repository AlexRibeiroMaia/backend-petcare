package com.petcare.pet_care.domain.veterinarian;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VeterinarianRepository {
    Veterinarian save(Veterinarian veterinarian);

    Optional<Veterinarian> findById(UUID id);

    Optional<Veterinarian> findByCrmv(String crmv);

    Optional<Veterinarian> findByEmail(String email);

    List<Veterinarian> findBySpecialty(String specialty);

    List<Veterinarian> findByName(String name);

    List<Veterinarian> findAll();

    void delete(Veterinarian veterinarian);
}
