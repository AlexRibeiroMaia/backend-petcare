package com.petcare.pet_care.infra.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtService.parseToken(token);
                String role = claims.get("role", String.class);
                String userId = claims.get("userId", String.class);
                String email = claims.get("email", String.class);
                String name = claims.get("name", String.class);
                String tutorId = claims.get("tutorId", String.class);
                String veterinarianId = claims.get("veterinarianId", String.class);

                AuthenticatedUser principal = new AuthenticatedUser(
                        userId != null ? UUID.fromString(userId) : null,
                        email,
                        name,
                        role != null ? com.petcare.pet_care.domain.user.UserRole.valueOf(role) : null,
                        tutorId != null ? UUID.fromString(tutorId) : null,
                        veterinarianId != null ? UUID.fromString(veterinarianId) : null
                );

                List<GrantedAuthority> authorities = role == null
                        ? List.of()
                        : List.of(new SimpleGrantedAuthority("ROLE_" + role));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(principal, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
