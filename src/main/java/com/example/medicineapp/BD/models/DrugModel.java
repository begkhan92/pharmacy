package com.example.medicineapp.BD.models;

import jakarta.persistence.*;

@Entity
public class DrugModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameDose;

    @ManyToOne
    @JoinColumn(name = "firma_id")
    private Firma firma;

    public Long getId() {
        return id;
    }

    public String getNameDose() {
        return nameDose;
    }

    public void setNameDose(String nameDose) {
        this.nameDose = nameDose;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }
}
