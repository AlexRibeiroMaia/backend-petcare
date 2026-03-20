package com.petcare.pet_care.adapters.inbound.controller;

import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationMedicalUpdateDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.consultationDtos.ConsultationResponseDto;
import com.petcare.pet_care.application.usecases.ConsultationUseCases;
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
@RequestMapping("/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationUseCases consultationUseCases;

    @PostMapping("/create")
    public ResponseEntity<ConsultationResponseDto> create(@RequestBody @Valid ConsultationRequestDto dto) {
        ConsultationResponseDto response = consultationUseCases.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponseDto> findById(@PathVariable Long id) {
        ConsultationResponseDto response = consultationUseCases.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<ConsultationResponseDto>> findAll() {
        List<ConsultationResponseDto> consultations = consultationUseCases.findAll();
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/veterinarian/{veterinarianId}/upcoming")
    public ResponseEntity<List<ConsultationResponseDto>> findUpcomingByVeterinarianId(
            @PathVariable UUID veterinarianId) {

        List<ConsultationResponseDto> consultations =
                consultationUseCases.findUpcomingByVeterinarianId(veterinarianId);
        return ResponseEntity.ok(consultations);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ConsultationResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid ConsultationMedicalUpdateDto dto) {

        ConsultationResponseDto updated = consultationUseCases.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultationUseCases.delete(id);
        return ResponseEntity.noContent().build();
    }
}
