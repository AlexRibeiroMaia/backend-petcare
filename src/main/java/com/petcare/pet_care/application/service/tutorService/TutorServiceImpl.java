package com.petcare.pet_care.application.service.tutorService;

import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.tutor.TutorDtoMapper;
import com.petcare.pet_care.application.exceptions.BadRequestException;
import com.petcare.pet_care.application.exceptions.ConflictException;
import com.petcare.pet_care.application.exceptions.ForbiddenException;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.TutorUseCases;
import com.petcare.pet_care.domain.consultation.ConsultationRepository;
import com.petcare.pet_care.domain.tutor.Tutor;
import com.petcare.pet_care.domain.tutor.TutorRepository;
import com.petcare.pet_care.domain.user.User;
import com.petcare.pet_care.domain.user.UserRepository;
import com.petcare.pet_care.domain.user.UserRole;
import com.petcare.pet_care.infra.security.AuthenticatedUser;
import com.petcare.pet_care.infra.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorUseCases {

    private final TutorRepository tutorRepository;
    private final TutorDtoMapper tutorDtoMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final ConsultationRepository consultationRepository;

    @Override
    @Transactional
    public TutorResponseDto create(TutorRequestDto dto) {
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new BadRequestException("Password is required");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("User with email " + dto.getEmail() + " already exists");
        }

        tutorRepository.findByEmail(dto.getEmail())
                .ifPresent(
                        t -> {
                            throw new ConflictException("Tutor with email " + dto.getEmail() + " already exists");
                        }
                );

        tutorRepository.findByCpf(dto.getCpf())
                .ifPresent(
                        t -> {
                            throw new ConflictException("Tutor with CPF " + dto.getCpf() + " already exists");
                        }
                );

        tutorRepository.findByPhone(dto.getPhone())
                .ifPresent(
                        t -> {
                            throw new ConflictException("Tutor with phone " + dto.getPhone() + " already exists");
                        }
                );

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.TUTOR);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        Tutor tutor = tutorDtoMapper.toDomain(dto);
        Tutor saved = tutorRepository.save(tutor);
        return tutorDtoMapper.toResponseDto(saved);
    }

    @Override
    public TutorResponseDto findById(UUID id) {
        securityUtil.requireRole(UserRole.ADMIN);
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public TutorResponseDto findByEmail(String email) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.VETERINARIAN);
        if (user.getVeterinarianId() == null) {
            throw new ForbiddenException("Access denied");
        }
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);

        if (!consultationRepository.existsByVeterinarianIdAndTutorId(user.getVeterinarianId(), tutor.getId())) {
            throw new ForbiddenException("Access denied");
        }

        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public TutorResponseDto findByCpf(String cpf) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.VETERINARIAN);
        if (user.getVeterinarianId() == null) {
            throw new ForbiddenException("Access denied");
        }
        Tutor tutor = tutorRepository.findByCpf(cpf)
                .orElseThrow(NotFoundException::new);

        if (!consultationRepository.existsByVeterinarianIdAndTutorId(user.getVeterinarianId(), tutor.getId())) {
            throw new ForbiddenException("Access denied");
        }

        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public TutorResponseDto findByPhone(String phone) {
        AuthenticatedUser user = securityUtil.requireRole(UserRole.VETERINARIAN);
        if (user.getVeterinarianId() == null) {
            throw new ForbiddenException("Access denied");
        }
        Tutor tutor = tutorRepository.findByPhone(phone)
                .orElseThrow(NotFoundException::new);

        if (!consultationRepository.existsByVeterinarianIdAndTutorId(user.getVeterinarianId(), tutor.getId())) {
            throw new ForbiddenException("Access denied");
        }

        return tutorDtoMapper.toResponseDto(tutor);
    }

    @Override
    public List<TutorResponseDto> findAll() {
        securityUtil.requireRole(UserRole.ADMIN);
        return tutorRepository.findAll().stream()
                .map(tutorDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<TutorResponseDto> findByPetId(UUID petId) {
        securityUtil.requireRole(UserRole.ADMIN);
        return tutorRepository.findByPetId(petId).stream()
                .map(tutorDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public TutorResponseDto update(UUID id, TutorRequestDto dto) {
        AuthenticatedUser user = securityUtil.getCurrentUser();
        if (!user.isAdmin() && !(user.isTutor() && id.equals(user.getTutorId()))) {
            throw new ForbiddenException("Access denied");
        }

        Tutor existing = tutorRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        if (!existing.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("User with email " + dto.getEmail() + " already exists");
        }

        if (!existing.getCpf().equals(dto.getCpf())) {
            tutorRepository.findByCpf(dto.getCpf())
                    .ifPresent(t -> {
                        if (!t.getId().equals(existing.getId())) {
                            throw new ConflictException("Tutor with CPF " + dto.getCpf() + " already exists");
                        }
                    });
        }

        if (!existing.getPhone().equals(dto.getPhone())) {
            tutorRepository.findByPhone(dto.getPhone())
                    .ifPresent(t -> {
                        if (!t.getId().equals(existing.getId())) {
                            throw new ConflictException("Tutor with phone " + dto.getPhone() + " already exists");
                        }
                    });
        }

        userRepository.findByEmail(existing.getEmail())
                .ifPresent(u -> {
                    u.setName(dto.getName());
                    u.setEmail(dto.getEmail());
                    userRepository.save(u);
                });

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setCpf(dto.getCpf());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());

        Tutor updated = tutorRepository.save(existing);
        return tutorDtoMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AuthenticatedUser user = securityUtil.getCurrentUser();
        if (!user.isAdmin() && !(user.isTutor() && id.equals(user.getTutorId()))) {
            throw new ForbiddenException("Access denied");
        }

        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        tutorRepository.deleteById(id);
        userRepository.findByEmail(tutor.getEmail())
                .ifPresent(u -> userRepository.deleteById(u.getId()));
    }

    @Override
    public boolean existsById(UUID id) {
        securityUtil.requireRole(UserRole.ADMIN);
        return tutorRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        securityUtil.requireRole(UserRole.ADMIN);
        return tutorRepository.existsByEmail(email);
    }
}
