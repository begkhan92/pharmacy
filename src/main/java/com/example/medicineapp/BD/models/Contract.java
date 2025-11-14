package com.example.medicineapp.BD.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Drug> drugs = new ArrayList<>();

    private int number;
    private LocalDate startDate;
    private LocalDate expireDate;
    private int licenceNumber;
    private int impNumber;
    private LocalDate birzaDate;
    private String name;
    private Boolean closed = false;
    private Boolean warning = true;

    // new variables
    private String actNumber;
    private LocalDate arrivedAt;
    private int numberOfCars = 0;

    /**
     * Closed is true only when:
     *  - contract-level fields are complete (fieldsComplete == true)
     *  - AND there is at least one drug and ALL drugs are closed
     */
    public void updateClosed() {
        boolean fieldsComplete = (
                actNumber != null &&
                        arrivedAt != null &&
                        numberOfCars != 0
        );

        // Treat null or empty drugs list as NOT all closed: contract cannot be closed without at least one drug.
        boolean allDrugsClosed = false;
        if (drugs != null && !drugs.isEmpty()) {
            allDrugsClosed = drugs.stream()
                    .allMatch(drug -> Boolean.TRUE.equals(drug.getClosed()));
        }

        this.closed = (fieldsComplete && allDrugsClosed);

        // debug log (optional)
        System.out.println("Contract.updateClosed -> fieldsComplete=" + fieldsComplete +
                " drugsSize=" + (drugs == null ? 0 : drugs.size()) +
                " allDrugsClosed=" + allDrugsClosed + " => closed=" + this.closed);
    }

    public String getActNumber() {
        return actNumber;
    }

    public void setActNumber(String actNumber) {
        this.actNumber = actNumber;
        updateClosed();
        updateWarning();
    }

    public LocalDate getArrivedAt() {
        return arrivedAt;
    }

    public void setArrivedAt(LocalDate arrivedAt) {
        this.arrivedAt = arrivedAt;
        updateClosed();
        updateWarning();
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
        updateClosed();
        updateWarning();
    }

    public void updateWarning() {
        // If drugs list is empty, warning = true might be meaningful, but here we set false when no drugs.
        if (drugs == null || drugs.isEmpty()) {
            this.warning = false;
            return;
        }

        // warning = !allClosed
        boolean allClosed = drugs.stream()
                .allMatch(drug -> Boolean.TRUE.equals(drug.getClosed()));

        this.warning = !allClosed;
    }

    public Long getId() {
        return id;
    }

    public List<Drug> getDrugs() {
        // keep warning up-to-date when reading
        updateWarning();
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        // clear existing relationships
        if (this.drugs != null) {
            this.drugs.forEach(d -> d.setContract(null));
        }
        this.drugs = drugs != null ? drugs : new ArrayList<>();
        // ensure bidirectional link
        for (Drug d : this.drugs) {
            if (d.getContract() != this) {
                d.setContract(this);
            }
        }
        updateClosed();
        updateWarning();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        updateClosed();
        updateWarning();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        updateClosed();
        updateWarning();
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
        updateClosed();
        updateWarning();
    }

    public int getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(int licenceNumber) {
        this.licenceNumber = licenceNumber;
        updateClosed();
        updateWarning();
    }

    public int getImpNumber() {
        return impNumber;
    }

    public void setImpNumber(int impNumber) {
        this.impNumber = impNumber;
        updateClosed();
        updateWarning();
    }

    public LocalDate getBirzaDate() {
        return birzaDate;
    }

    public void setBirzaDate(LocalDate birzaDate) {
        this.birzaDate = birzaDate;
        updateClosed();
        updateWarning();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateClosed();
        updateWarning();
    }

    public Boolean getClosed() {
        return closed;
    }

    public Boolean getWarning() {
        return warning;
    }

    /**
     * Adds drug and keeps bidirectional relationship. Recalculates contract status.
     */
    public void addDrug(Drug drug) {
        if (drug == null) return;
        if (!drugs.contains(drug)) {
            drugs.add(drug);
        }
        if (drug.getContract() != this) {
            // setContract will ensure old contract is cleaned up
            drug.setContract(this);
        }
        updateClosed();
        updateWarning();
    }

    /**
     * Removes a drug and updates contract state.
     */
    public void removeDrug(Drug drug) {
        if (drug == null) return;
        if (drugs.remove(drug)) {
            // clear link from drug side
            if (drug.getContract() == this) {
                drug.setContract(null);
            }
            updateClosed();
            updateWarning();
        }
    }
}
