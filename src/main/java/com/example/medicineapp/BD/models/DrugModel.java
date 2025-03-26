package com.example.medicineapp.BD.models;

import jakarta.persistence.*;

@Entity
public class DrugModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameDose;

    private String firma;

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
}
