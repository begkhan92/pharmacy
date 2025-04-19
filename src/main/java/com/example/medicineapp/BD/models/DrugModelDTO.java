package com.example.medicineapp.BD.models;

public class DrugModelDTO {
    private Long id;
    private String nameDose;
    private String firma;
    private boolean selected; // For UI highlighting or pre-filling dropzone

    public Long getId() {
        return id;
    }

    public String getNameDose() {
        return nameDose;
    }

    public String getFirma() {
        return firma;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNameDose(String nameDose) {
        this.nameDose = nameDose;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

