package com.petcare.pet_care.application.service.petService;

import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.pet.PetDtoMapper;
import com.petcare.pet_care.application.usecases.PetUseCases;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetUseCases {

    private final PetRepository petRepository;
    private final PetDtoMapper petDtoMapper;

    @Override
    public PetResponseDto create(PetRequestDto dto) {
        Pet pet = petDtoMapper.toDomain(dto);
        Pet saved = petRepository.save(pet);
        return petDtoMapper.toResponseDto(saved);
    }

    @Override
    public PetResponseDto findById(UUID id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com id: " + id));
        return petDtoMapper.toResponseDto(pet);
    }

    @Override
    public List<PetResponseDto> findAll() {
        return petRepository.findAll()
                .stream()
                .map(petDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PetResponseDto> findByTutorId(UUID tutorId) {
        return petRepository.findByTutorId(tutorId)
                .stream()
                .map(petDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PetResponseDto> findByEspecie(String especie) {
        return petRepository.findByEspecie(especie)
                .stream()
                .map(petDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public PetResponseDto findByIdAndTutorId(UUID id, UUID tutorId) {
        Pet pet = petRepository.findByIdAndTutorId(id, tutorId)
                .orElseThrow(() -> new RuntimeException(
                        "Pet não encontrado com id: " + id + " para o tutor: " + tutorId));
        return petDtoMapper.toResponseDto(pet);
    }

    @Override
    public long countByEspecie(String especie) {
        return petRepository.countByEspecie(especie);
    }

    @Override
    public PetResponseDto update(UUID id, PetRequestDto dto) {
        Pet existing = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com id: " + id));

        existing.setName(dto.getName());
        existing.setEspecie(dto.getEspecie());
        existing.setRace(dto.getRace());
        existing.setBirthDate(dto.getBirthDate());
        existing.setWeight(dto.getWeight());
        existing.setSex(dto.getSex());
        existing.setTutor(dto.getTutor());

        Pet updated = petRepository.save(existing);
        return petDtoMapper.toResponseDto(updated);
    }

    @Override
    public void delete(UUID id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com id: " + id));
        petRepository.delete(pet);
    }
}