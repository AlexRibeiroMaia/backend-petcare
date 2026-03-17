package com.petcare.pet_care.adapters.outbound.persistence.pet;

import com.petcare.pet_care.adapters.inbound.rest.pet.PetDtoMapper;
import com.petcare.pet_care.adapters.outbound.entities.JpaPetEntity;
import com.petcare.pet_care.domain.pet.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetMapper {

    public Pet toDomain(JpaPetEntity entity) {
        if (entity == null) {
            return null;
        }

        Pet pet = new Pet();
        pet.setId(entity.getId());
        pet.setName(entity.getName());
        pet.setEspecie(entity.getEspecie());
        pet.setRace(entity.getRace());
        pet.setBirthDate(entity.getBirthdate());
        pet.setWeight(entity.getWeight());
        pet.setSex(entity.getSex());
        pet.setCadasterDate(entity.getCadasterDate());


        if (entity.getTutor() != null) {
            pet.setTutor(entity.getTutor().getId());
        }

        return pet;
    }

    public JpaPetEntity toJpaEntity(Pet pet) {
        if (pet == null) {
            return null;
        }

        JpaPetEntity entity = new JpaPetEntity();
        entity.setId(pet.getId());
        entity.setName(pet.getName());
        entity.setEspecie(pet.getEspecie());
        entity.setRace(pet.getRace());
        entity.setBirthdate(pet.getBirthDate());
        entity.setWeight(pet.getWeight());
        entity.setSex(pet.getSex());
        entity.setCadasterDate(pet.getCadasterDate());

        // Tutor e coleções também podem ser mapeados aqui
        // quando houver necessidade de carregar as associações.

        return entity;
    }
}
