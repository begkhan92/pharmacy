package com.example.medicineapp.BD.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actNumber;
    private String invoiceNumber;
    private int numberOftransport;
    private String CMRNumber;
    private LocalDate dateArrived;
    private LocalDate dateClosed;
//    private Firma firma;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargo_id")  // This creates a foreign key in DrugModel
    private List<Drug> drugs;

    // Constructors, getters, setters
    public Cargo() {}

    public Cargo(String actNumber) {
        this.actNumber = actNumber;
    }

    public String getActNumber() {
        return actNumber;
    }

    public Long getId() {
        return id;
    }

    public void setActNumber(String actNumber) {
        this.actNumber = actNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public int getNumberOftransport() {
        return numberOftransport;
    }

    public void setNumberOftransport(int numberOftransport) {
        this.numberOftransport = numberOftransport;
    }

    public String getCMRNumber() {
        return CMRNumber;
    }

    public void setCMRNumber(String CMRNumber) {
        this.CMRNumber = CMRNumber;
    }

    public LocalDate getDateArrived() {
        return dateArrived;
    }

    public void setDateArrived(LocalDate dateArrived) {
        this.dateArrived = dateArrived;
    }

    public LocalDate getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDate dateClosed) {
        this.dateClosed = dateClosed;
    }
//
//    public Firma getFirma() {
//        return firma;
//    }
//
//    public void setFirma(Firma firma) {
//        this.firma = firma;
//    }

    //    public List<Drug> getDrugs() {
//        return drugs;
//    }
//
//    public void setDrugs(List<Drug> drugs) {
//        this.drugs = drugs;
//    }
}