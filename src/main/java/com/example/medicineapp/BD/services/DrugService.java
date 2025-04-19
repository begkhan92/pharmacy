package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.repositories.DrugRepository;
import com.example.medicineapp.BD.specifications.DrugSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    public void saveDrug(Drug drug) {

        drugRepository.save(drug);
    }

    public Drug getDrugById(Long id) {
        return drugRepository.findById(id).orElseThrow(() -> new RuntimeException("Derman tapylmady"));
    }

    public List<Drug> findByInvoiceId(Long invoiceId) {
        return drugRepository.findByInvoiceId(invoiceId);
    }

    public void deleteDrug(Long id) {
        drugRepository.deleteById(id);
    }

    public Drug updateDrug(Long id, Drug updatedDrug) {
        Drug existingDrug = getDrugById(id);
        existingDrug.setName(updatedDrug.getName());
        existingDrug.setFirma(updatedDrug.getFirma());
        existingDrug.setQuantity(updatedDrug.getQuantity());
        existingDrug.setProductionDate(updatedDrug.getProductionDate());
        existingDrug.setSeriesNumber(updatedDrug.getSeriesNumber());
        existingDrug.setPrice(updatedDrug.getPrice());
        existingDrug.setAnalizeCertification(updatedDrug.getAnalizeCertification());
        existingDrug.setExpireDate(updatedDrug.getExpireDate());


        drugRepository.save(existingDrug);
        return existingDrug;
    }

    public List<Drug> filterDrugs(Long invoiceId, Long contractId, String name, String firma, Boolean isClosed, String contractNumber) {
        if (invoiceId == null && contractId == null) {
            throw new IllegalArgumentException("both of invoiceId and contractId cannot be null");
        }

        Specification<Drug> spec = Specification
                .where(DrugSpecifications.hasInvoiceId(invoiceId))
                .and(DrugSpecifications.hasContractId(contractId))
                .and(DrugSpecifications.hasName(name))
                .and(DrugSpecifications.hasFirma(firma))
                .and(DrugSpecifications.hasContactNumber(contractNumber))
                .and(DrugSpecifications.hasClosedStatus(isClosed));

        return drugRepository.findAll(spec);
    }

}

