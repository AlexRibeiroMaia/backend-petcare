package com.petcare.pet_care.domain;


import com.petcare.pet_care.domain.enums.DeviceStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Device {
    private Long id;
    private String serialNumber;
    private String model;
    private LocalDateTime activatedAt;
    private DeviceStatus status;
    private Pet pet;
    private List<Monitoring> monitorings = new ArrayList<>();

    public Device() {

    }
    public Device(Long id, String serialNumber, String model, LocalDateTime activatedAt, DeviceStatus status, Pet pet, List<Monitoring> monitorings) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.model = model;
        this.activatedAt = activatedAt;
        this.status = status;
        this.pet = pet;
        this.monitorings = monitorings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(LocalDateTime activatedAt) {
        this.activatedAt = activatedAt;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public List<Monitoring> getMonitorings() {
        return monitorings;
    }

    public void setMonitorings(List<Monitoring> monitorings) {
        this.monitorings = monitorings;
    }
}
