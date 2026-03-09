package com.petcare.pet_care.domain.alert;

import com.petcare.pet_care.domain.enums.AlertGravity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AlertRequestDTO {
    @NotNull(message = "Pet ID is required")
    private UUID petId;

    @NotBlank(message = "Alert type is required")
    @Size(max = 100, message = "Alert type must have at most 100 characters")
    private String typeAlert;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must have at most 255 characters")
    private String description;

    @NotNull(message = "Alert gravity is required")
    private AlertGravity alertGravity;

    public AlertRequestDTO() {
    }

    public AlertRequestDTO(UUID petId, String typeAlert, String description, AlertGravity alertGravity) {
        this.petId = petId;
        this.typeAlert = typeAlert;
        this.description = description;
        this.alertGravity = alertGravity;
    }

}
