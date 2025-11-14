package com.example.medicineapp.BD.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String firma;
    private Integer quantity;
    private LocalDate productionDate;
    private LocalDate expireDate;
    private String seriesNumber;
    private BigDecimal price;
    private String analizeCertification;

    // new variables: lisenziya, tomojnu, TDS, birza
    private LocalDate lisenceRequest;
    private LocalDate lisenceResponse;
    private LocalDate tomojniRequest;
    private LocalDate tomojniResponse;
    private LocalDate tdsRequest;
    private LocalDate tdsResponse;
    private LocalDate birzaRequest;
    private LocalDate birzaResponse;

    private String invoiceNumber;
    private String contractNumber;
    private String lisenceCykdy;
    private String tomojniCykdy;
    private String birzaCykdy;
    private String tdsCykdy;
    private String lisenceNumber;
    private String fromWhere;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    @JsonBackReference
    private Contract contract;

    private Boolean closed = false;

    // ----------------- relationships & helper --------------------------------
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Contract getContract() {
        return contract;
    }

    /**
     * Safely set the contract while keeping both sides consistent.
     */
    public void setContract(Contract newContract) {
        if (this.contract == newContract) {
            return;
        }

        Contract old = this.contract;
        if (old != null) {
            if (old.getDrugs().contains(this)) {
                old.getDrugs().remove(this);
            }
            old.updateClosed();
            old.updateWarning();
        }

        this.contract = newContract;

        if (newContract != null) {
            if (!newContract.getDrugs().contains(this)) {
                newContract.getDrugs().add(this);
            }
            newContract.updateClosed();
            newContract.updateWarning();
        }

        updateIsClosed();
    }

    // ----------------- getters/setters --------------------------------------

    public LocalDate getLisenceRequest() {
        return lisenceRequest;
    }

    public void setLisenceRequest(LocalDate lisenceRequest) {
        this.lisenceRequest = lisenceRequest;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (lisenceRequest != null) {
            this.lisenceCykdy = "Tabşyryldy<br>" + lisenceRequest.format(formatter) + "<br>"
                    + ((this.lisenceNumber != null) ? this.lisenceNumber : "");
        }

        if (this.lisenceRequest != null && this.lisenceResponse != null) {
            this.lisenceCykdy = "Çykdy<br>" +
                    this.lisenceRequest.format(formatter) + "<br>" +
                    this.lisenceResponse.format(formatter) + "<br>" +
                    ((this.lisenceNumber != null) ? this.lisenceNumber : "");
        }
        updateIsClosed();
    }

    public String getLisenceNumber() {
        return lisenceNumber;
    }

    public void setLisenceNumber(String lisenceNumber) {
        this.lisenceNumber = lisenceNumber;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
        updateIsClosed();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        updateIsClosed();
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        updateIsClosed();
    }

    public LocalDate getLisenceResponse() {
        return lisenceResponse;
    }

    public void setLisenceResponse(LocalDate lisenceResponse) {
        this.lisenceResponse = lisenceResponse;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (this.lisenceRequest != null && this.lisenceResponse != null) {
            this.lisenceCykdy = "Çykdy<br>" +
                    this.lisenceRequest.format(formatter) + "<br>" +
                    this.lisenceResponse.format(formatter) + "<br>" +
                    ((this.lisenceNumber != null) ? this.lisenceNumber : "");
        }
        updateIsClosed();
    }

    public LocalDate getTomojniRequest() {
        return tomojniRequest;
    }

    public void setTomojniRequest(LocalDate tomojniRequest) {
        this.tomojniRequest = tomojniRequest;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (tomojniRequest != null) {
            this.tomojniCykdy = "Tabşyryldy<br>" + tomojniRequest.format(formatter);
        }

        if (this.tomojniRequest != null && this.tomojniResponse != null) {
            this.tomojniCykdy = "Çykdy<br>" +
                    this.tomojniRequest.format(formatter) + "<br>" +
                    this.tomojniResponse.format(formatter);
        }

        updateIsClosed();
    }

    public LocalDate getTomojniResponse() {
        return tomojniResponse;
    }

    public void setTomojniResponse(LocalDate tomojniResponse) {
        this.tomojniResponse = tomojniResponse;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (this.tomojniRequest != null && this.tomojniResponse != null) {
            this.tomojniCykdy = "Çykdy<br>" +
                    this.tomojniRequest.format(formatter) + "<br>" +
                    this.tomojniResponse.format(formatter);
        }
        updateIsClosed();
    }

    public LocalDate getTdsRequest() {
        return tdsRequest;
    }

    public void setTdsRequest(LocalDate tdsRequest) {
        this.tdsRequest = tdsRequest;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (tdsRequest != null) {
            this.tdsCykdy = "Tabşyryldy<br>" + tdsRequest.format(formatter);
        }

        if (this.tdsRequest != null && this.tdsResponse != null) {
            this.tdsCykdy = "Çykdy<br>" +
                    this.tdsResponse.format(formatter) + "<br>" +
                    this.tdsRequest.format(formatter);
        }

        updateIsClosed();
    }

    public LocalDate getTdsResponse() {
        return tdsResponse;
    }

    public void setTdsResponse(LocalDate tdsResponse) {
        this.tdsResponse = tdsResponse;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (this.tdsRequest != null && this.tdsResponse != null) {
            this.tdsCykdy = "Çykdy<br>" +
                    this.tdsResponse.format(formatter) + "<br>" +
                    this.tdsRequest.format(formatter);
        }
        updateIsClosed();
    }

    public void updateIsClosed() {
        boolean oldClosed = Boolean.TRUE.equals(this.closed);

        this.closed = (name != null && !name.isEmpty()
                && fromWhere != null && !fromWhere.isEmpty()
                && quantity != null
                && invoiceNumber != null && !invoiceNumber.isEmpty()
                && contractNumber != null && !contractNumber.isEmpty()
                && lisenceNumber != null && !lisenceNumber.isEmpty()
                && lisenceCykdy != null && lisenceCykdy.startsWith("Çykdy")
                && tomojniCykdy != null && tomojniCykdy.startsWith("Çykdy")
                && birzaCykdy != null && birzaCykdy.startsWith("Çykdy")
                && tdsCykdy != null && tdsCykdy.startsWith("Çykdy"));

        if (contract != null && oldClosed != this.closed) {
            contract.updateClosed();
            contract.updateWarning();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateIsClosed();
    }

    public LocalDate getBirzaRequest() {
        return birzaRequest;
    }

    public void setBirzaRequest(LocalDate birzaRequest) {
        this.birzaRequest = birzaRequest;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (birzaRequest != null) {
            this.birzaCykdy = "Tabşyryldy<br>" + birzaRequest.format(formatter);
        }

        if (this.birzaRequest != null && this.birzaResponse != null) {
            this.birzaCykdy = "Çykdy<br>" +
                    this.birzaRequest.format(formatter) + "<br>" +
                    this.birzaResponse.format(formatter);
        }
        updateIsClosed();
    }

    public LocalDate getBirzaResponse() {
        return birzaResponse;
    }

    public void setBirzaResponse(LocalDate birzaResponse) {
        this.birzaResponse = birzaResponse;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (this.birzaRequest != null && this.birzaResponse != null) {
            this.birzaCykdy = "Çykdy<br>" +
                    this.birzaRequest.format(formatter) + "<br>" +
                    this.birzaResponse.format(formatter);
        }
        updateIsClosed();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        updateIsClosed();
    }

    public String getLisenceCykdy() {
        return lisenceCykdy;
    }

    public void setLisenceCykdy(String lisenceCykdy) {
        this.lisenceCykdy = lisenceCykdy;
        updateIsClosed();
    }

    public String getTomojniCykdy() {
        return tomojniCykdy;
    }

    public void setTomojniCykdy(String tomojniCykdy) {
        this.tomojniCykdy = tomojniCykdy;
        updateIsClosed();
    }

    public String getBirzaCykdy() {
        return birzaCykdy;
    }

    public void setBirzaCykdy(String birzaCykdy) {
        this.birzaCykdy = birzaCykdy;
        updateIsClosed();
    }

    public String getTdsCykdy() {
        return tdsCykdy;
    }

    public void setTdsCykdy(String tdsCykdy) {
        this.tdsCykdy = tdsCykdy;
        updateIsClosed();
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
        updateIsClosed();
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
        updateIsClosed();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        updateIsClosed();
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
        updateIsClosed();
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
        updateIsClosed();
    }

    public String getAnalizeCertification() {
        return analizeCertification;
    }

    public void setAnalizeCertification(String analizeCertification) {
        this.analizeCertification = analizeCertification;
        updateIsClosed();
    }

    public Boolean getClosed() {
        return closed;
    }
}
