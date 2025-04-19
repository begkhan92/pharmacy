package com.example.medicineapp.BD.models;

public class DrugDTO {
    private Long id;
    private String name;
    private String firma;
    private String contractNumber;

    public DrugDTO(Drug drug) {
        this.id = drug.getId();
        this.name = drug.getName();
        this.firma = drug.getFirma();
        this.contractNumber = drug.getContract() != null
                ? String.valueOf(drug.getContract().getNumber())
                : null;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirma() {
        return firma;
    }

    public String getContractNumber() {
        return contractNumber;
    }


}

