package com.petcare.pet_care.adapters.inbound.rest.tutor;

import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorResponseDto;
import com.petcare.pet_care.domain.tutor.Tutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TutorDtoMapper {

    public Tutor toDomain(TutorRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Tutor tutor = new Tutor();
        tutor.setName(dto.getName());
        tutor.setEmail(dto.getEmail());
        tutor.setCpf(dto.getCpf());
        tutor.setPhone(dto.getPhone());
        tutor.setAddress(dto.getAddress());
        tutor.setCadasterDate(LocalDateTime.now());

        return tutor;
    }

    public TutorResponseDto toResponseDto(Tutor tutor) {
        if (tutor == null) {
            return null;
        }

        return TutorResponseDto.builder()
                .id(tutor.getId())
                .name(tutor.getName())
                .email(tutor.getEmail())
                .cpf(tutor.getCpf())
                .phone(tutor.getPhone())
                .address(tutor.getAddress())
                .cadasterDate(tutor.getCadasterDate())
                .build();
    }
}
