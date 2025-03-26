package com.example.medicineapp.BD.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actNumber;
    //    private List<Invoice> invoices;
    private int numberOftransport;
    private String CMRNumber;

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

//    public List<Invoice> getInvoices() {
//        return invoices;
//    }

//    public void setInvoices(List<Invoice> invoices) {
//        this.invoices = invoices;
//    }


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

//    public List<Drug> getDrugs() {
//        return drugs;
//    }
//
//    public void setDrugs(List<Drug> drugs) {
//        this.drugs = drugs;
//    }
}