package com.petcare.pet_care.adapters.outbound.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@With
@Getter
@Entity
@Table(name = "consulta")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JpaConsultationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;

    @NotNull(message = "Data e hora são obrigatorias")
    @Future(message = "Data da consulta deve ser no futuro")
    @Column(name = "data_hora")
    private LocalDate date;

    @Column(name = "diagnostico")
    private String diagnosis;

    @Column(name = "prescricao")
    private String prescription;

    @Column(name = "observacoes")
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet", nullable = false)
    @ToString.Exclude
    private JpaPetEntity pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veterinario", nullable = false)
    @ToString.Exclude
    private JpaVeterinarianEntity veterinarian;
}
