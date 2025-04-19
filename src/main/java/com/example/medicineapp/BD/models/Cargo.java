package com.example.medicineapp.BD.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Invoice> invoices = new ArrayList<>();

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

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setCargo(this);
    }
}