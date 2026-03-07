package com.petcare.pet_care.domain;

import com.petcare.pet_care.domain.enums.AlertGravity;
import com.petcare.pet_care.domain.enums.AlertStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Alert {
    private Long id;

    private String typeAlert;

    private String description;

    private LocalDateTime dateAlert;

    private AlertGravity alertGravity;

    private AlertStatus alertStatus;

    private Pet pet;

    public Alert() {

    }

    public Alert(Long id, String typeAlert, String description, LocalDateTime dateAlert, AlertGravity alertGravity, AlertStatus alertStatus) {
        this.id = id;
        this.typeAlert = typeAlert;
        this.description = description;
        this.dateAlert = dateAlert;
        this.alertGravity = alertGravity;
        this.alertStatus = alertStatus;
        this.pet = new Pet();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAlert() {
        return typeAlert;
    }

    public void setTypeAlert(String typeAlert) {
        this.typeAlert = typeAlert;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateAlert() {
        return dateAlert;
    }

    public void setDateAlert(LocalDateTime dateAlert) {
        this.dateAlert = dateAlert;
    }

    public AlertGravity getAlertGravity() {
        return alertGravity;
    }

    public void setAlertGravity(AlertGravity alertGravity) {
        this.alertGravity = alertGravity;
    }

    public AlertStatus getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(AlertStatus alertStatus) {
        this.alertStatus = alertStatus;
    }

    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
