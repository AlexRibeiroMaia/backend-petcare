package com.petcare.pet_care.adapters.inbound.rest.pet;

import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetResponseDto;
import com.petcare.pet_care.domain.pet.Pet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PetDtoMapper {

    public Pet toDomain(PetRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setEspecie(dto.getEspecie());
        pet.setRace(dto.getRace());
        pet.setBirthDate(dto.getBirthDate());
        pet.setWeight(dto.getWeight());
        pet.setSex(dto.getSex());
        // setTutor(UUID) — o domínio recebe apenas o UUID do tutor
        pet.setTutor(dto.getTutorId());
        pet.setCadasterDate(LocalDateTime.now());

        return pet;
    }

    public PetResponseDto toResponseDto(Pet pet) {
        if (pet == null) {
            return null;
        }

        return PetResponseDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .especie(pet.getEspecie())
                .race(pet.getRace())
                .birthDate(pet.getBirthDate())
                .weight(pet.getWeight())
                .sex(pet.getSex())
                .cadasterDate(pet.getCadasterDate())
                .tutorId(pet.getTutor())
                .build();
    }
}