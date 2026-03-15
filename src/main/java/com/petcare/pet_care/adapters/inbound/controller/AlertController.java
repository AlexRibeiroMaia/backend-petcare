package com.petcare.pet_care.adapters.inbound.controller;

import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertRequestDTO;
import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertResponseDTO;
import com.petcare.pet_care.adapters.inbound.rest.alert.AlertDtoMapper;
import com.petcare.pet_care.adapters.outbound.entities.JpaPetEntity;
import com.petcare.pet_care.adapters.outbound.repositories.JpaPetRepository;
import com.petcare.pet_care.application.usecases.AlertUseCases;
import com.petcare.pet_care.domain.alert.Alert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertUseCases alertUseCases;
    private final AlertDtoMapper alertDtoMapper;

    @PostMapping
    public ResponseEntity<AlertResponseDTO> create(@Valid @RequestBody AlertRequestDTO request) {
        Alert alert = alertDtoMapper.toDomain(request);
        Alert created = alertUseCases.createAlert(alert);
        return ResponseEntity.status(HttpStatus.CREATED).body(alertDtoMapper.toResponse(created));
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<AlertResponseDTO> resolve(@PathVariable Long id) {
        Alert resolved = alertUseCases.resolveAlert(id);
        return ResponseEntity.ok(alertDtoMapper.toResponse(resolved));
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<AlertResponseDTO>> findByPet(@PathVariable UUID petId) {
        List<AlertResponseDTO> response = alertUseCases.findAlertsByPetId(petId).stream()
                .map(alertDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pets/{petId}/pending")
    public ResponseEntity<List<AlertResponseDTO>> findPendingByPet(@PathVariable UUID petId) {
        List<AlertResponseDTO> response = alertUseCases.findPendingAlertsByPetId(petId).stream()
                .map(alertDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tutors/{tutorId}")
    public ResponseEntity<List<AlertResponseDTO>> findByTutor(@PathVariable UUID tutorId) {
        List<AlertResponseDTO> response = alertUseCases.findAlertsByTutorId(tutorId).stream()
                .map(alertDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending/count")
    public ResponseEntity<Long> countPending() {
        return ResponseEntity.ok(alertUseCases.countPendingAlerts());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<AlertResponseDTO>> recent(@RequestParam(defaultValue = "1") int days) {
        List<AlertResponseDTO> response = alertUseCases.findRecentAlerts().stream()
                .map(alertDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}



