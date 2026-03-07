package com.petcare.pet_care.adapters.outbound.entities;



import com.petcare.pet_care.domain.enums.AlertGravity;
import com.petcare.pet_care.domain.enums.AlertStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@With
@Getter
@Entity
@Table(name = "alerta")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JpaAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    @NotBlank(message = "Tipo do alerta é obrigatorio")
    @Column(name = "tipo_alerta")
    private String typeAlert;

    @NotBlank(message = "Descrição é obrigatoria")
    @Column(name = "descricao")
    private String description;

    @NotNull(message = "Data e hora são obrigatorias")
    @Column(name = "data_hora")
    private LocalDateTime dateAlert;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravidade")
    private AlertGravity alertGravity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AlertStatus alertStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet")
    @ToString.Exclude
    private JpaPetEntity pet;

    @PrePersist
    protected void onCreate() {
        if (dateAlert == null) {
            dateAlert = LocalDateTime.now();
        }
        if (alertStatus == null) {
            alertStatus = AlertStatus.PENDING;
        }
    }
}
