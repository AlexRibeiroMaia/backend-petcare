package com.petcare.pet_care.adapters.outbound.entities;

import com.petcare.pet_care.domain.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @NotBlank(message = "Nome é obrigatorio")
    @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres")
    private String name;

    @NotBlank(message = "Espécie é obrigatoria")
    private String especie;

    @NotBlank(message = "Raça é obrigatoria")
    private String race;

    @NotNull(message = "Data de nascimento é obrigatoria")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthdate;

    @NotNull(message = "Peso é obrigatorio")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que zero")//para validar o peso
    private Double weight;

    @Enumerated(EnumType.STRING)
    private Sex sex;

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
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        cadasterDate = LocalDateTime.now();
    }

}
