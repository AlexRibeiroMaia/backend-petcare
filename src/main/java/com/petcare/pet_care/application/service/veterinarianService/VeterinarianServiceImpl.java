package com.petcare.pet_care.application.service.veterinarianService;

import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.veterinarian.VeterinarianDtoMapper;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.VeterinarianUseCases;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import com.petcare.pet_care.domain.veterinarian.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VeterinarianServiceImpl implements VeterinarianUseCases {

    private final VeterinarianRepository veterinarianRepository;
    private final VeterinarianDtoMapper veterinarianDtoMapper;

    @Override
    public VeterinarianResponseDto create(VeterinarianRequestDto dto) {
        Veterinarian veterinarian = veterinarianDtoMapper.toDomain(dto);
        Veterinarian saved = veterinarianRepository.save(veterinarian);
        return veterinarianDtoMapper.toResponseDto(saved);
    }

    @Override
    public VeterinarianResponseDto findById(UUID id) {
        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return veterinarianDtoMapper.toResponseDto(veterinarian);
    }

    @Override
    public VeterinarianResponseDto findByCrmv(String crmv) {
        Veterinarian veterinarian = veterinarianRepository.findByCrmv(crmv)
                .orElseThrow(NotFoundException::new);
        return veterinarianDtoMapper.toResponseDto(veterinarian);
    }

    @Override
    public VeterinarianResponseDto findByEmail(String email) {
        Veterinarian veterinarian = veterinarianRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
        return veterinarianDtoMapper.toResponseDto(veterinarian);
    }

    @Override
    public List<VeterinarianResponseDto> findBySpecialty(String specialty) {
        return veterinarianRepository.findBySpecialty(specialty).stream()
                .map(veterinarianDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VeterinarianResponseDto> findByName(String name) {
        return veterinarianRepository.findByName(name).stream()
                .map(veterinarianDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VeterinarianResponseDto> findAll() {
        return veterinarianRepository.findAll().stream()
                .map(veterinarianDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public VeterinarianResponseDto update(UUID id, VeterinarianRequestDto dto) {
        Veterinarian existing = veterinarianRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        existing.setName(dto.getName());
        existing.setSpecialty(dto.getSpecialty());
        existing.setPhone(dto.getPhone());
        existing.setEmail(dto.getEmail());
        existing.setCrmv(dto.getCrmv());

        Veterinarian updated = veterinarianRepository.save(existing);
        return veterinarianDtoMapper.toResponseDto(updated);
    }

    @Override
    public void delete(UUID id) {
        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        veterinarianRepository.delete(veterinarian);
    }
}
