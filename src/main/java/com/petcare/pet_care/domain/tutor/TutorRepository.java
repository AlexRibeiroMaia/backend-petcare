package com.petcare.pet_care.domain.tutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TutorRepository {
    Tutor save(Tutor tutor);

    Optional<Tutor> findById(UUID id);

    Optional<Tutor> findByEmail(String email);

    Optional<Tutor> findByCpf(String cpf);

    Optional<Tutor> findByPhone(String phone);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    List<Tutor> findByAdress(String adress);

    List<Tutor> findAll();

    void deleteById(UUID id);

    List<Tutor> findByPetId(UUID petId);
}
