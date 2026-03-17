package com.petcare.pet_care.adapters.inbound.controller;


import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetRequestDto;
import com.petcare.pet_care.adapters.inbound.dtos.petDtos.PetResponseDto;
import com.petcare.pet_care.adapters.inbound.rest.pet.PetDtoMapper;
import com.petcare.pet_care.application.usecases.PetUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetUseCases petUseCases;
    private final PetDtoMapper petDtoMapper;

    @PostMapping("/create")
    public ResponseEntity<PetResponseDto> create(@RequestBody @Valid PetRequestDto dto) {
        PetResponseDto response = petUseCases.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> findById(@PathVariable UUID id) {
        PetResponseDto response = petUseCases.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("find-all")
    public ResponseEntity<List<PetResponseDto>> findAll() {
        List<PetResponseDto> pets = petUseCases.findAll();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<PetResponseDto>> findByTutorId(@PathVariable UUID tutorId) {
        List<PetResponseDto> pets = petUseCases.findByTutorId(tutorId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<PetResponseDto>> findByEspecie(@PathVariable String especie) {
        List<PetResponseDto> pets = petUseCases.findByEspecie(especie);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}/tutor/{tutorId}")
    public ResponseEntity<PetResponseDto> findByIdAndTutorId(
            @PathVariable UUID id,
            @PathVariable UUID tutorId) {

        PetResponseDto pet = petUseCases.findByIdAndTutorId(id, tutorId);
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid PetRequestDto dto) {

        PetResponseDto updatedPet = petUseCases.update(id, dto);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        petUseCases.delete(id);
        return ResponseEntity.noContent().build();
    }
}
