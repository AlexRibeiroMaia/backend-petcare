package com.petcare.pet_care.adapters.outbound.entities;

import com.petcare.pet_care.domain.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@With
@Getter
@Entity
@Table(name = "pet")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JpaPetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_pet")
    private UUID id;

    @NotBlank(message = "Nome é obrigatorio")
    @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres")
    @Column(name = "nome")
    private String name;

    @NotBlank(message = "Espécie é obrigatoria")
    @Column(name = "especie")
    private String especie;

    @NotBlank(message = "Raça é obrigatoria")
    @Column(name = "raca")
    private String race;

    @NotNull(message = "Data de nascimento é obrigatoria")
    @Past(message = "Data de nascimento deve ser no passado")
    @Column(name = "data_nascimento")
    private LocalDate birthDate;

    @NotNull(message = "Peso é obrigatorio")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que zero")//para validar o peso
    @Column(name = "peso")
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sex sex;

    @Column(name = "data_cadastro")
    private LocalDateTime cadasterDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    @ToString.Exclude // para nao incluir tutor ao ToString
    private JpaTutorEntity tutor;

    @OneToMany(mappedBy = "pet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<JpaDeviceEntity> devices = new ArrayList<>();

    @OneToMany(mappedBy = "pet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<JpaMonitoringEntity> monitorings = new ArrayList<>();

    @OneToMany(mappedBy = "pet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<JpaAlertEntity> alerts = new ArrayList<>();

    @PrePersist //faz o metodo ser executado automaticamente antes de uma entidade ser persistida no banco de dados
    protected void onCreate() {
        cadasterDate = LocalDateTime.now();
    }

}
