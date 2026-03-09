package com.petcare.pet_care.domain.monitoring;

import com.petcare.pet_care.domain.pet.Pet;
import com.petcare.pet_care.domain.device.Device;
import com.petcare.pet_care.domain.enums.ActivityLevel;

import java.time.LocalDateTime;

public class Monitoring {

    private Long id;
    private LocalDateTime date;
    private Double corporalTemperature;
    private Integer heartHate;
    private ActivityLevel activityLevel;
    private Double latitude;
    private Double longitude;
    private Integer beatsPerMinute;
    private Device device;
    private Pet pet;

    public Monitoring() {

    }

    public Monitoring(Long id, LocalDateTime date, Double corporalTemperature, Integer heartHate, ActivityLevel activityLevel, Double latitude, Double longitude, Integer beatsPerMinute, Device device, Pet pet) {
        this.id = id;
        this.date = date;
        this.corporalTemperature = corporalTemperature;
        this.heartHate = heartHate;
        this.activityLevel = activityLevel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.beatsPerMinute = beatsPerMinute;
        this.device = device;
        this.pet = pet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getCorporalTemperature() {
        return corporalTemperature;
    }

    public void setCorporalTemperature(Double corporalTemperature) {
        this.corporalTemperature = corporalTemperature;
    }

    public Integer getHeartHate() {
        return heartHate;
    }

    public void setHeartHate(Integer heartHate) {
        this.heartHate = heartHate;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getBeatsPerMinute() {
        return beatsPerMinute;
    }

    public void setBeatsPerMinute(Integer beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
