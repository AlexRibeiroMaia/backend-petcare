package com.petcare.pet_care.adapters.inbound.controller;

import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertRequestDTO;
import com.petcare.pet_care.adapters.inbound.dtos.alertDtos.AlertResponseDTO;
import com.petcare.pet_care.application.usecases.AlertUseCases;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertUseCases alertUseCases;

    @PostMapping
    public ResponseEntity<AlertResponseDTO> create(@Valid @RequestBody AlertRequestDTO request) {
        AlertResponseDTO created = alertUseCases.createAlert(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<AlertResponseDTO> resolve(@PathVariable Long id) {
        AlertResponseDTO resolved = alertUseCases.resolveAlert(id);
        return ResponseEntity.ok(resolved);
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<?> findByPet(@PathVariable UUID petId) {
        List<AlertResponseDTO> response = alertUseCases.findAlertsByPetId(petId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No pet with ID =>> " + petId));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pets/{petId}/pending")
    public ResponseEntity<List<AlertResponseDTO>> findPendingByPet(@PathVariable UUID petId) {
        List<AlertResponseDTO> response = alertUseCases.findPendingAlertsByPetId(petId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tutors/{tutorId}")
    public ResponseEntity<?> findByTutor(@PathVariable UUID tutorId) {
        List<AlertResponseDTO> response = alertUseCases.findAlertsByTutorId(tutorId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No tutor with ID =>> " + tutorId));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending/count")
    public ResponseEntity<Long> countPending() {
        return ResponseEntity.ok(alertUseCases.countPendingAlerts());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<AlertResponseDTO>> recent(@RequestParam(defaultValue = "1") int days) {
        List<AlertResponseDTO> response = alertUseCases.findRecentAlerts();
        return ResponseEntity.ok(response);
    }
}


