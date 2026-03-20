package com.petcare.pet_care.adapters.inbound.controller;

import com.petcare.pet_care.adapters.inbound.dtos.authDtos.AuthRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.authDtos.AuthResponseDto;
import com.petcare.pet_care.application.usecases.AuthUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCases authUseCases;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto dto) {
        AuthResponseDto response = authUseCases.login(dto);
        return ResponseEntity.ok(response);
    }
}
