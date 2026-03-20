package com.petcare.pet_care.application.service.veterinarianService;

import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.veterinarian.VeterinarianDtoMapper;
import com.petcare.pet_care.application.exceptions.BadRequestException;
import com.petcare.pet_care.application.exceptions.ConflictException;
import com.petcare.pet_care.application.exceptions.ForbiddenException;
import com.petcare.pet_care.application.exceptions.NotFoundException;
import com.petcare.pet_care.application.usecases.VeterinarianUseCases;
import com.petcare.pet_care.domain.user.User;
import com.petcare.pet_care.domain.user.UserRepository;
import com.petcare.pet_care.domain.user.UserRole;
import com.petcare.pet_care.domain.veterinarian.Veterinarian;
import com.petcare.pet_care.domain.veterinarian.VeterinarianRepository;
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
public class VeterinarianServiceImpl implements VeterinarianUseCases {

    private final VeterinarianRepository veterinarianRepository;
    private final VeterinarianDtoMapper veterinarianDtoMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public VeterinarianResponseDto create(VeterinarianRequestDto dto) {
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new BadRequestException("Password is required");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("User with email " + dto.getEmail() + " already exists");
        }

        veterinarianRepository.findByCrmv(dto.getCrmv())
                .ifPresent(
                        v -> {
                            throw new ConflictException("Veterinarian with CRMV " + dto.getCrmv() + " already exists");
                        }
                );

        veterinarianRepository.findByEmail(dto.getEmail())
                .ifPresent(
                        v -> {
                            throw new ConflictException("Veterinarian with email " + dto.getEmail() + " already exists");
                        }
                );

        veterinarianRepository.findByPhone(dto.getPhone())
                .ifPresent(
                        v -> {
                            throw new ConflictException("Veterinarian with phone " + dto.getPhone() + " already exists");
                        }
                );

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.VETERINARIAN);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        Veterinarian veterinarian = veterinarianDtoMapper.toDomain(dto);
        Veterinarian saved = veterinarianRepository.save(veterinarian);
        return veterinarianDtoMapper.toResponseDto(saved);
    }

    @Override
    public VeterinarianResponseDto findById(UUID id) {
        securityUtil.requireRole(UserRole.ADMIN);
        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return veterinarianDtoMapper.toResponseDto(veterinarian);
    }

    @Override
    public VeterinarianResponseDto findByCrmv(String crmv) {
        securityUtil.requireRole(UserRole.TUTOR, UserRole.ADMIN);
        Veterinarian veterinarian = veterinarianRepository.findByCrmv(crmv)
                .orElseThrow(() -> new NotFoundException("CRMV not found, verify and try again"));
        return veterinarianDtoMapper.toResponseDto(veterinarian);
    }

    @Override
    public VeterinarianResponseDto findByEmail(String email) {
        securityUtil.requireRole(UserRole.TUTOR, UserRole.ADMIN);
        Veterinarian veterinarian = veterinarianRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
        return veterinarianDtoMapper.toResponseDto(veterinarian);
    }

    @Override
    public List<VeterinarianResponseDto> findBySpecialty(String specialty) {
        securityUtil.requireRole(UserRole.TUTOR, UserRole.ADMIN);
        return veterinarianRepository.findBySpecialty(specialty).stream()
                .map(veterinarianDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VeterinarianResponseDto> findByName(String name) {
        securityUtil.requireRole(UserRole.TUTOR, UserRole.ADMIN);
        return veterinarianRepository.findByName(name).stream()
                .map(veterinarianDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VeterinarianResponseDto> findAll() {
        securityUtil.requireRole(UserRole.ADMIN);
        return veterinarianRepository.findAll().stream()
                .map(veterinarianDtoMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public VeterinarianResponseDto update(UUID id, VeterinarianRequestDto dto) {
        AuthenticatedUser user = securityUtil.getCurrentUser();
        if (!user.isAdmin() && !(user.isVeterinarian() && id.equals(user.getVeterinarianId()))) {
            throw new ForbiddenException("Access denied");
        }

        Veterinarian existing = veterinarianRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        if (!existing.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("User with email " + dto.getEmail() + " already exists");
        }

        if (!existing.getCrmv().equals(dto.getCrmv())) {
            veterinarianRepository.findByCrmv(dto.getCrmv())
                    .ifPresent(v -> {
                        if (!v.getId().equals(existing.getId())) {
                            throw new ConflictException("Veterinarian with CRMV " + dto.getCrmv() + " already exists");
                        }
                    });
        }

        if (!existing.getPhone().equals(dto.getPhone())) {
            veterinarianRepository.findByPhone(dto.getPhone())
                    .ifPresent(v -> {
                        if (!v.getId().equals(existing.getId())) {
                            throw new ConflictException("Veterinarian with phone " + dto.getPhone() + " already exists");
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
        existing.setSpecialty(dto.getSpecialty());
        existing.setPhone(dto.getPhone());
        existing.setEmail(dto.getEmail());
        existing.setCrmv(dto.getCrmv());

        Veterinarian updated = veterinarianRepository.save(existing);
        return veterinarianDtoMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AuthenticatedUser user = securityUtil.getCurrentUser();
        if (!user.isAdmin() && !(user.isVeterinarian() && id.equals(user.getVeterinarianId()))) {
            throw new ForbiddenException("Access denied");
        }

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        veterinarianRepository.delete(veterinarian);
        userRepository.findByEmail(veterinarian.getEmail())
                .ifPresent(u -> userRepository.deleteById(u.getId()));
    }
}
