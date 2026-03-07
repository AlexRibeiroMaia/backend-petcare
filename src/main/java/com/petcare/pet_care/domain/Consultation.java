package com.petcare.pet_care.domain;

import java.time.LocalDate;

public class Consultation {
    private Long id;
    private LocalDate date;
    private String diagnosis;
    private String prescription;
    private String observation;
    private Pet pet;
    private Veterinarian veterinarian;

    public Consultation() {

    }
    public Consultation(Long id, LocalDate date, String diagnosis, String prescription, String observation, Pet pet, Veterinarian veterinarian) {
        this.id = id;
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.observation = observation;
        this.pet = pet;
        this.veterinarian = veterinarian;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }
}
