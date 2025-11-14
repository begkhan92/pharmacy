package com.example.medicineapp.BD.models;

import jakarta.persistence.*;

@Entity
@Table(name = "drug_model")
public class DrugModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_dose", columnDefinition = "TEXT")
    private String nameDose;

    private String firma;
    private String unit;

    public Long getId() {
        return id;
    }

    public String getNameDose() {
        return nameDose;
    }

    public void setNameDose(String nameDose) {
        this.nameDose = nameDose;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
