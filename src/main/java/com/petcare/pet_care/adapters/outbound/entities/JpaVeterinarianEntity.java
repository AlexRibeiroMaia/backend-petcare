package com.petcare.pet_care.adapters.outbound.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@With
@Getter
@Entity
@Table(name = "veterinario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JpaVeterinarianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_veterinario" )
    private UUID id;

    @NotBlank(message = "Nome é obrigatorio")
    @Column(name = "nome")
    private String name;

    @NotBlank(message = "Especialidade é obrigatoria")
    @Column(name = "especialidade")
    private String specialty;

    @NotBlank(message = "Telefone é obrigatorio")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    @Column(name = "telefone", unique = true)
    private String phone;

    @NotBlank(message = "Email é obrigatorio")
    @Email(message = "Email deve ser válido")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "CRMV é obrigatorio")
    @Column(name = "crmv", unique = true)
    private String crmv;

    @OneToMany(mappedBy = "veterinarian", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<JpaConsultationEntity> consultations = new ArrayList<>();
}
