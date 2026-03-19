package com.petcare.pet_care.adapters.inbound.controller;

import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.tutorDtos.TutorResponseDto;
import com.petcare.pet_care.application.usecases.TutorUseCases;
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
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController {

    private final TutorUseCases tutorUseCases;

    @PostMapping("/create")
    public ResponseEntity<TutorResponseDto> create(@RequestBody @Valid TutorRequestDto dto) {
        TutorResponseDto response = tutorUseCases.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorResponseDto> findById(@PathVariable UUID id) {
        TutorResponseDto response = tutorUseCases.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TutorResponseDto> findByEmail(@PathVariable String email) {
        TutorResponseDto response = tutorUseCases.findByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<TutorResponseDto> findByCpf(@PathVariable String cpf) {
        TutorResponseDto response = tutorUseCases.findByCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<TutorResponseDto> findByPhone(@PathVariable String phone) {
        TutorResponseDto response = tutorUseCases.findByPhone(phone);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<TutorResponseDto>> findAll() {
        List<TutorResponseDto> response = tutorUseCases.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<TutorResponseDto>> findByPetId(@PathVariable UUID petId) {
        List<TutorResponseDto> response = tutorUseCases.findByPetId(petId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID id) {
        return ResponseEntity.ok(tutorUseCases.existsById(id));
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(tutorUseCases.existsByEmail(email));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TutorResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid TutorRequestDto dto) {

        TutorResponseDto updated = tutorUseCases.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tutorUseCases.delete(id);
        return ResponseEntity.noContent().build();
    }
}
