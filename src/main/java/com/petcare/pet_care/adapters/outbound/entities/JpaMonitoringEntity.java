package com.petcare.pet_care.adapters.outbound.entities;

import com.petcare.pet_care.domain.enums.ActivityLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@With
@Getter
@Entity
@Table(name = "monitoramento")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JpaMonitoringEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monitoramento")
    private Long id;

    @NotNull(message = "Data e hora são obrigatorias")
    @Column(name = "data_hora")
    private LocalDateTime date;

    @NotNull(message = "Temperatura corporal é obrigatoria")
    @DecimalMin(value = "30.0", message = "Temperatura corporal minina é de 30.0")
    @DecimalMax(value = "45.0", message = "Temperatura corporal maxima é 45 C")
    @Column(name = "temperatura_corporal")
    private Double corporalTemperature;

    @NotNull(message = "Frequencia cardiaca é obrigatoria")
    @Column(name = "frequencia_cardiaca")
    private Integer heartHate;

    @NotNull(message = "Nivel de atividade é obrigatorio")
    @Min(value = 0, message = "nivel de atividade nao pode ser nagativo")
    @Column(name = "nivel_atividade")
    private ActivityLevel activityLevel;

    @NotNull(message = "Latitude é obrigatoria")
    @Column(name = "latitude")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatoria")
    @Column(name = "longitude")
    private Double longitude;

    @NotNull(message = "Batimentos por minuto são obrigatorios")
    @Column(name = "batimentos_por_minuto")
    private Integer beatsPerMinute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dispositivo")
    @ToString.Exclude
    private JpaDeviceEntity device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet")
    @ToString.Exclude
    private JpaPetEntity pet;

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }
}
