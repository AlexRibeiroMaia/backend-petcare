package com.petcare.pet_care.adapters.outbound.persistence.veterinarian;

import com.petcare.pet_care.adapters.outbound.entities.JpaVeterinarianEntity;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import org.springframework.stereotype.Component;

@Component
public class VeterinarianMapper {

    public Veterinarian toDomain(JpaVeterinarianEntity entity) {
        if (entity == null) {
            return null;
        }

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setId(entity.getId());
        veterinarian.setName(entity.getName());
        veterinarian.setSpecialty(entity.getSpecialty());
        veterinarian.setPhone(entity.getPhone());
        veterinarian.setEmail(entity.getEmail());
        veterinarian.setCrmv(entity.getCrmv());

        return veterinarian;
    }

    public JpaVeterinarianEntity toJpaEntity(Veterinarian veterinarian) {
        if (veterinarian == null) {
            return null;
        }

        return JpaVeterinarianEntity.builder()
                .id(veterinarian.getId())
                .name(veterinarian.getName())
                .specialty(veterinarian.getSpecialty())
                .phone(veterinarian.getPhone())
                .email(veterinarian.getEmail())
                .crmv(veterinarian.getCrmv())
                .build();
    }
}
