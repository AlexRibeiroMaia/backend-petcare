package com.petcare.pet_care.infra.security;

import com.petcare.pet_care.domain.user.UserRole;

import java.util.UUID;

public class AuthenticatedUser {
    private final UUID userId;
    private final String email;
    private final String name;
    private final UserRole role;
    private final UUID tutorId;
    private final UUID veterinarianId;

    public AuthenticatedUser(UUID userId, String email, String name, UserRole role, UUID tutorId, UUID veterinarianId) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.tutorId = tutorId;
        this.veterinarianId = veterinarianId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public UUID getTutorId() {
        return tutorId;
    }

    public UUID getVeterinarianId() {
        return veterinarianId;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isTutor() {
        return role == UserRole.TUTOR;
    }

    public boolean isVeterinarian() {
        return role == UserRole.VETERINARIAN;
    }
}
