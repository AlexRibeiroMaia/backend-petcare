package com.petcare.pet_care.application.service.tutorService;

import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.tutor.TutorDtoMapper;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.TutorUseCases;
import com.petcare.pet_care.domain.tutor.Tutor;
import com.petcare.pet_care.domain.tutor.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorUseCases {

    private final TutorRepository tutorRepository;
    private final TutorDtoMapper tutorDtoMapper;

    @Override
    public TutorResponseDto create(TutorRequestDto dto) {
        Tutor tutor = tutorDtoMapper.toDomain(dto);
        Tutor saved = tutorRepository.save(tutor);
        return tutorDtoMapper.toResponseDto(saved);
    }

    @Override
    public TutorResponseDto findById(UUID id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public TutorResponseDto findByEmail(String email) {
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public TutorResponseDto findByCpf(String cpf) {
        Tutor tutor = tutorRepository.findByCpf(cpf)
                .orElseThrow(NotFoundException::new);
        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public TutorResponseDto findByPhone(String phone) {
        Tutor tutor = tutorRepository.findByPhone(phone)
                .orElseThrow(NotFoundException::new);
        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public List<TutorResponseDto> findAll() {
        return tutorRepository.findAll().stream()
                .map(tutorDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<TutorResponseDto> findByPetId(UUID petId) {
        return tutorRepository.findByPetId(petId).stream()
                .map(tutorDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public TutorResponseDto update(UUID id, TutorRequestDto dto) {
        Tutor existing = tutorRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setCpf(dto.getCpf());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());

        Tutor updated = tutorRepository.save(existing);
        return tutorDtoMapper.toResponseDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (!tutorRepository.existsById(id)) {
            throw new NotFoundException();
        }
        tutorRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return tutorRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return tutorRepository.existsByEmail(email);
    }
}
