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

}
