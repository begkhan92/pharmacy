package com.example.medicineapp.BD.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DrugModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameDose;

    public String getNameDose() {
        return nameDose;
    }

    public void setNameDose(String nameDose) {
        this.nameDose = nameDose;
    }
}
