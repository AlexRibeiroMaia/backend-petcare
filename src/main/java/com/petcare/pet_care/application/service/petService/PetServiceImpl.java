package com.petcare.pet_care.application.service.petService;

import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.pet.PetDtoMapper;
import com.petcare.pet_care.application.exceptions.ForbiddenException;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.PetUseCases;
import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.pet.PetRepository;
import com.petcare.pet_care.domain.user.UserRole;
import com.petcare.pet_care.infra.security.AuthenticatedUser;
import com.petcare.pet_care.infra.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetUseCases {

    private final PetRepository petRepository;
    private final PetDtoMapper petDtoMapper;
    private final SecurityUtil securityUtil;

    @Override
    public PetResponseDto create(PetRequestDto dto) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);
        if (user.getTutorId() == null) {
            throw new ForbiddenException("Access denied");
        }

        Pet pet = petDtoMapper.toDomain(dto);
        pet.setTutor(user.getTutorId());

        Pet saved = petRepository.save(pet);
        return petDtoMapper.toResponseDto(saved);
    }

    @Override
    public PetResponseDto findById(UUID id) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);
        Pet pet = petRepository.findByIdAndTutorId(id, user.getTutorId())
                .orElseThrow(NotFoundException::new);
        return petDtoMapper.toResponseDto(pet);
    }

    @Override
    public List<PetResponseDto> findAll() {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);
        return petRepository.findByTutorId(user.getTutorId())
                .stream()
                .map(petDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PetResponseDto> findByTutorId(UUID tutorId) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);
        if (!user.getTutorId().equals(tutorId)) {
            throw new ForbiddenException("Access denied");
        }
        return petRepository.findByTutorId(user.getTutorId())
                .stream()
                .map(petDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PetResponseDto> findByEspecie(String especie) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);
        return petRepository.findByTutorId(user.getTutorId())
                .stream()
                .filter(pet -> pet.getEspecie() != null && pet.getEspecie().equalsIgnoreCase(especie))
                .map(petDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public PetResponseDto findByIdAndTutorId(UUID id, UUID tutorId) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);
        if (!user.getTutorId().equals(tutorId)) {
            throw new ForbiddenException("Access denied");
        }
        Pet pet = petRepository.findByIdAndTutorId(id, user.getTutorId())
                .orElseThrow(NotFoundException::new);
        return petDtoMapper.toResponseDto(pet);
    }

    @Override
    public PetResponseDto update(UUID id, PetRequestDto dto) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);

        Pet existing = petRepository.findByIdAndTutorId(id, user.getTutorId())
                .orElseThrow(NotFoundException::new);

        existing.setName(dto.getName());
        existing.setEspecie(dto.getEspecie());
        existing.setRace(dto.getRace());
        existing.setBirthDate(dto.getBirthDate());
        existing.setWeight(dto.getWeight());
        existing.setSex(dto.getSex());
        existing.setTutor(user.getTutorId());

        Pet updated = petRepository.save(existing);
        return petDtoMapper.toResponseDto(updated);
    }

    @Override
    public void delete(UUID id) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.TUTOR);
        Pet pet = petRepository.findByIdAndTutorId(id, user.getTutorId())
                .orElseThrow(NotFoundException::new);
        petRepository.delete(pet);
    }
}
