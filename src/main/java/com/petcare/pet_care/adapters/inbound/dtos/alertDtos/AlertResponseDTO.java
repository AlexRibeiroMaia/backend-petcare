package com.petcare.pet_care.adapters.inbound.dtos.alertDtos;

import com.petcare.pet_care.domain.enums.AlertGravity;
import com.petcare.pet_care.domain.enums.AlertStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class AlertResponseDTO {
    private Long id;
    private UUID petId;
    private String typeAlert;
    private String description;
    private LocalDateTime dateAlert;
    private AlertGravity alertGravity;
    private AlertStatus alertStatus;

    public AlertResponseDTO() {
    }

    public AlertResponseDTO(Long id, UUID petId, String typeAlert, String description, LocalDateTime dateAlert,
                            AlertGravity alertGravity, AlertStatus alertStatus) {
        this.id = id;
        this.petId = petId;
        this.typeAlert = typeAlert;
        this.description = description;
        this.dateAlert = dateAlert;
        this.alertGravity = alertGravity;
        this.alertStatus = alertStatus;
    }

}
