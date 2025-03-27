package com.example.medicineapp.BD.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String firma;
    private Integer quantity;
    private String contractNumber;
    private LocalDate productionDate;
    private LocalDate expireDate;
    private String seriesNumber;
    private BigDecimal price;
    private String analizeCertification;
    @ManyToOne
    @JoinColumn(name = "cargo_id")  // Allow inserting and updating cargo_id
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "contract_id")  // Allow inserting and updating cargo_id
    private Contract contract;


    private Boolean isClosed;

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Boolean getIsClosed() {
        this.isClosed = (name != null && firma != null && quantity != null &&
                contractNumber != null && expireDate != null &&
  productionDate != null && seriesNumber != null && price != null && analizeCertification != null);

        return isClosed;
    }

    // Getters and setters


    public Long getId() {
        return id;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
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

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getAnalizeCertification() {
        return analizeCertification;
    }

    public void setAnalizeCertification(String analizeCertification) {
        this.analizeCertification = analizeCertification;
    }
}

