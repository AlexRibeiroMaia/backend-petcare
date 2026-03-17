package com.petcare.pet_care.domain.pet;

import com.petcare.pet_care.domain.alert.Alert;
import com.petcare.pet_care.domain.device.Device;
import com.petcare.pet_care.domain.enums.Sex;
import com.petcare.pet_care.domain.monitoring.Monitoring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pet {

    private UUID id;
    private String name;
    private String especie;
    private String race;
    private LocalDate birthDate;
    private Double weight;
    private Sex sex;
    private LocalDateTime cadasterDate;
    private UUID tutor;
    private List<Device> devices = new ArrayList<>();
    private List<Monitoring> monitorings = new ArrayList<>();
    private List<Alert> alerts = new ArrayList<>();

    public Pet() {

    }

    public Pet(UUID id, String name, String especies, String race, String birthDate, Double weight, Sex sex, LocalDateTime CadasterDate, UUID tutor, List<Device> devices, List<Monitoring> monitorings, List<Alert> alerts) {
        this.id = id;
        this.name = name;
        this.especie = especies;
        this.race = race;
        this.birthDate = LocalDate.parse(birthDate);
        this.weight = weight;
        this.sex = sex;
        this.cadasterDate = CadasterDate;
        this.tutor = tutor;
        this.devices = devices;
        this.monitorings = monitorings;
        this.alerts = alerts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRace() {
        return race;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Double getWeight() {
        return weight;
    }

    public Sex getSex() {
        return sex;
    }

    public LocalDateTime getCadasterDate() {
        return cadasterDate;
    }

    public UUID getTutor() {
        return tutor;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public List<Monitoring> getMonitorings() {
        return monitorings;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setCadasterDate(LocalDateTime cadasterDate) {
        this.cadasterDate = cadasterDate;
    }

    public void setTutor(UUID tutor) {
        this.tutor = tutor;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setMonitorings(List<Monitoring> monitorings) {
        this.monitorings = monitorings;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }
}
