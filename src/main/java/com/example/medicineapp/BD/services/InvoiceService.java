package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.*;
import com.example.medicineapp.BD.repositories.CargoRepository;
import com.example.medicineapp.BD.repositories.InvoiceRepository;
import com.example.medicineapp.BD.specifications.CargoSpecification;
import com.example.medicineapp.BD.specifications.InvoiceSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    private List<Drug> drugs = new ArrayList<>();
    private List<DrugsAndInvoices> drugsAndInvoicesList = new ArrayList<>();

    public void setSelectedDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public List<Drug> getSelectedDrugs() {
        return drugs;
    }

    public List<DrugsAndInvoices> getDrugsAndInvoicesList() {
        return drugsAndInvoicesList;
    }

    public void setDrugsAndInvoicesList(List<DrugsAndInvoices> drugsAndInvoicesList) {
        this.drugsAndInvoicesList = drugsAndInvoicesList;
    }

    public List<Invoice> filterInvoices(Integer year, Integer month) {


        Specification<Invoice> spec = Specification
                .where(InvoiceSpecifications.includeInYearAndMonth(year, month));
//                .and(DrugSpecifications.hasName(name))
//                .and(DrugSpecifications.hasFirma(firma))
//                .and(DrugSpecifications.hasContactNumber(contractNumber))
//                .and(DrugSpecifications.hasClosedStatus(isClosed));



        return invoiceRepository.findAll(spec);
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with ID: " + id));
    }

    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

}
