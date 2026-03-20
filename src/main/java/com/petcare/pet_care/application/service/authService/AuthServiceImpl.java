package com.petcare.pet_care.application.service.authService;

import com.petcare.pet_care.adapters.inbound.dtos.authDtos.AuthRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.authDtos.AuthResponseDto;
import com.petcare.pet_care.application.exceptions.UnauthorizedException;
import com.petcare.pet_care.application.usecases.AuthUseCases;
import com.petcare.pet_care.domain.tutor.TutorRepository;
import com.petcare.pet_care.domain.user.User;
import com.petcare.pet_care.domain.user.UserRepository;
import com.petcare.pet_care.domain.user.UserRole;
import com.petcare.pet_care.domain.veterinarian.VeterinarianRepository;
import com.petcare.pet_care.infra.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthUseCases {

    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto login(AuthRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        UUID tutorId = null;
        UUID veterinarianId = null;

        if (user.getRole() == UserRole.TUTOR) {
            tutorId = tutorRepository.findByEmail(user.getEmail())
                    .map(t -> t.getId())
                    .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        }

        if (user.getRole() == UserRole.VETERINARIAN) {
            veterinarianId = veterinarianRepository.findByEmail(user.getEmail())
                    .map(v -> v.getId())
                    .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        }

        String token = jwtService.generateToken(user, tutorId, veterinarianId);
        long expiresAt = Instant.now()
                .plus(jwtService.getExpirationMinutes(), ChronoUnit.MINUTES)
                .toEpochMilli();

        return AuthResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresAt(expiresAt)
                .build();
    }
}
