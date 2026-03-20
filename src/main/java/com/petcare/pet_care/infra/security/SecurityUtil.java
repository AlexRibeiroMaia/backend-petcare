package com.petcare.pet_care.infra.security;

import com.petcare.pet_care.application.exceptions.ForbiddenException;
import com.petcare.pet_care.application.exceptions.UnauthorizedException;
import com.petcare.pet_care.domain.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SecurityUtil {

    public AuthenticatedUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AuthenticatedUser authenticatedUser)) {
            throw new UnauthorizedException("Unauthorized");
        }

        return authenticatedUser;
    }

    public AuthenticatedUser requireRole(UserRole... roles) {
        AuthenticatedUser user = getCurrentUser();
        if (roles == null || roles.length == 0) {
            return user;
        }

        boolean allowed = Arrays.stream(roles)
                .anyMatch(role -> role == user.getRole());

        if (!allowed) {
            throw new ForbiddenException("Access denied");
        }

        return user;
    }
}
