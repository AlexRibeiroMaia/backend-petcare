package com.petcare.pet_care.adapters.inbound.rest.veterinarian;

import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianResponseDto;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import org.springframework.stereotype.Component;

@Component
public class VeterinarianDtoMapper {

    public Veterinarian toDomain(VeterinarianRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setName(dto.getName());
        veterinarian.setSpecialty(dto.getSpecialty());
        veterinarian.setPhone(dto.getPhone());
        veterinarian.setEmail(dto.getEmail());
        veterinarian.setCrmv(dto.getCrmv());

        return veterinarian;
    }

    public VeterinarianResponseDto toResponseDto(Veterinarian veterinarian) {
        if (veterinarian == null) {
            return null;
        }

        return VeterinarianResponseDto.builder()
                .id(veterinarian.getId())
                .name(veterinarian.getName())
                .specialty(veterinarian.getSpecialty())
                .phone(veterinarian.getPhone())
                .email(veterinarian.getEmail())
                .crmv(veterinarian.getCrmv())
                .build();
    }
}
