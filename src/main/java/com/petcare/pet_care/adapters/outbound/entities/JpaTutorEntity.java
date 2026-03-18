package com.petcare.pet_care.adapters.outbound.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@With
@Getter
@Entity
@Table(name = "tutor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JpaTutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    public UUID id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
    @Column(unique = true, length = 14)
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    @Column(unique = true)
    private String phone;

    @NotBlank(message = "Endereço é obrigatorio")
    @Size(max = 200, message = "Endereço deve ter no maximo 200 caracteres")
    @Column
    private String address;

    @Column(name = "data_cadastro")
    private LocalDateTime cadasterDate;

    @OneToMany(mappedBy = "tutor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<JpaPetEntity> pets = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        cadasterDate = LocalDateTime.now();
    }
}
