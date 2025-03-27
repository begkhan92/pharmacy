package com.example.medicineapp.BD.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    private List<Drug> drugs;

    private int number;
     private LocalDate startDate;
    private LocalDate expireDate;
    private int licenceNumber;
    private int impNumber;
    private LocalDate birzaDate;
    private String name;

    public Long getId() {
        return id;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public int getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(int licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public int getImpNumber() {
        return impNumber;
    }

    public void setImpNumber(int impNumber) {
        this.impNumber = impNumber;
    }

    public LocalDate getBirzaDate() {
        return birzaDate;
    }

    public void setBirzaDate(LocalDate birzaDate) {
        this.birzaDate = birzaDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
