package com.petcare.pet_care.adapters.outbound.entities;

import com.petcare.pet_care.domain.enums.DeviceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@With
@Getter
@Entity
@Table(name = "dispositivo")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JpaDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numero de serie é obrigatorio")
    @Column(name = "numero_serie", unique = true)
    private String serialNumber;

    @NotBlank(message = "Modelo é obrigatorio")
    @Column(name = "modelo")
    private String model;

    @Column(name = "data_ativacao")
    private LocalDateTime activatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeviceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet")
    @ToString.Exclude
    private JpaPetEntity pet;

    @OneToMany(mappedBy = "device",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<JpaMonitoringEntity> monitorings = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (activatedAt == null) {
            activatedAt = LocalDateTime.now();
        }

        if (status == null) {
            status = DeviceStatus.ACTIVE;
        }
    }
}
