package com.petcare.pet_care.domain.veterinarian;

import com.petcare.pet_care.domain.consultation.Consultation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Veterinarian {
    private UUID id;
    private String name;
    private String specialty;
    private String phone;
    private String email;
    private String crmv;
    private List<Consultation> consultations = new ArrayList<>();

    public Veterinarian() {}

    public Veterinarian(String name, String specialty, String phone, String email, String crmv, List<Consultation> consultations) {
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
        this.crmv = crmv;
        this.consultations = consultations;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
