package com.example.medicineapp.BD.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DrugWithQuantityDTO {
    private Long id;
    private String name;
    private String firma;
    private int quantity; // from DrugsAndInvoices
    private LocalDate productionDate;
    private LocalDate expireDate;
    private String seriesNumber;
    private BigDecimal price;
    private String analizeCertification;

    // constructor, getters, setters


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAnalizeCertification() {
        return analizeCertification;
    }

    public void setAnalizeCertification(String analizeCertification) {
        this.analizeCertification = analizeCertification;
    }
}

