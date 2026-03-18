package com.petcare.pet_care.adapters.outbound.persistence.tutor;

import com.petcare.pet_care.adapters.outbound.entities.JpaTutorEntity;
import com.petcare.pet_care.domain.tutor.Tutor;
import org.springframework.stereotype.Component;

@Component
public class TutorMapper {

    public Tutor toDomain(JpaTutorEntity entity) {
        if (entity == null) {
            return null;
        }

        Tutor tutor = new Tutor();
        tutor.setId(entity.getId());
        tutor.setName(entity.getName());
        tutor.setEmail(entity.getEmail());
        tutor.setCpf(entity.getCpf());
        tutor.setPhone(entity.getPhone());
        tutor.setAddress(entity.getAddress());
        tutor.setCadasterDate(entity.getCadasterDate());

        return tutor;
    }

    public JpaTutorEntity toJpaEntity(Tutor tutor) {
        if (tutor == null) {
            return null;
        }

        JpaTutorEntity entity = new JpaTutorEntity();
        entity.setId(tutor.getId());
        entity.setName(tutor.getName());
        entity.setEmail(tutor.getEmail());
        entity.setCpf(tutor.getCpf());
        entity.setPhone(tutor.getPhone());
        entity.setAddress(tutor.getAddress());
        entity.setCadasterDate(tutor.getCadasterDate());

        // Pets e associações podem ser mapeados aqui quando houver necessidade.

        return entity;
    }
}
