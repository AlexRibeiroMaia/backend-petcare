package com.petcare.pet_care.domain.consultation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultationRepository {
    Consultation save(Consultation consultation);

    Optional<Consultation> findById(Long id);

    List<Consultation> findAll();

    List<Consultation> findUpcomingByVeterinarianId(UUID veterinarianId);

    void delete(Consultation consultation);
}
