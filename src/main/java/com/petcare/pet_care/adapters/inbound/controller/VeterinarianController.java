package com.petcare.pet_care.adapters.inbound.controller;

import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.veterinarianDtos.VeterinarianResponseDto;
import com.petcare.pet_care.application.usecases.VeterinarianUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veterinarian")
@RequiredArgsConstructor
public class VeterinarianController {

    private final VeterinarianUseCases veterinarianUseCases;

    @PostMapping("/create")
    public ResponseEntity<VeterinarianResponseDto> create(@RequestBody @Valid VeterinarianRequestDto dto) {
        VeterinarianResponseDto response = veterinarianUseCases.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianResponseDto> findById(@PathVariable UUID id) {
        VeterinarianResponseDto response = veterinarianUseCases.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/crmv/{crmv}")
    public ResponseEntity<VeterinarianResponseDto> findByCrmv(@PathVariable String crmv) {
        VeterinarianResponseDto response = veterinarianUseCases.findByCrmv(crmv);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<VeterinarianResponseDto> findByEmail(@PathVariable String email) {
        VeterinarianResponseDto response = veterinarianUseCases.findByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<VeterinarianResponseDto>> findBySpecialty(@PathVariable String specialty) {
        List<VeterinarianResponseDto> response = veterinarianUseCases.findBySpecialty(specialty);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<VeterinarianResponseDto>> findByName(@PathVariable String name) {
        List<VeterinarianResponseDto> response = veterinarianUseCases.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<VeterinarianResponseDto>> findAll() {
        List<VeterinarianResponseDto> response = veterinarianUseCases.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VeterinarianResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid VeterinarianRequestDto dto) {

        VeterinarianResponseDto updated = veterinarianUseCases.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        veterinarianUseCases.delete(id);
        return ResponseEntity.noContent().build();
    }
}
