package com.petcare.pet_care.domain.tutor;

import com.petcare.pet_care.domain.pet.Pet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tutor {

    private UUID id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String address;
    private LocalDateTime cadasterDate;
    private List<Pet> pets = new ArrayList<>();

    public Tutor() {

    }
    public Tutor(UUID id, String name, String email, String cpf, String phone, String address, LocalDateTime cadasterDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.cadasterDate = cadasterDate;
        this.pets = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCadasterDate() {
        return cadasterDate;
    }

    public void setCadasterDate(LocalDateTime cadasterDate) {
        this.cadasterDate = cadasterDate;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
