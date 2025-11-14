package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugWithQuantityDTO;
import com.example.medicineapp.BD.models.DrugsAndInvoices;
import com.example.medicineapp.BD.repositories.DrugRepository;
import com.example.medicineapp.BD.repositories.DrugsAndInvoicesRepository;
import com.example.medicineapp.BD.specifications.DrugSpecifications;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DrugService {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private DrugsAndInvoicesRepository drugsAndInvoicesRepository;

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
        existingDrug.setContractNumber(updatedDrug.getContractNumber());
        existingDrug.setInvoiceNumber(updatedDrug.getInvoiceNumber());
        existingDrug.setFromWhere(updatedDrug.getFromWhere());

        existingDrug.setLisenceNumber(updatedDrug.getLisenceNumber());

        existingDrug.setLisenceRequest(updatedDrug.getLisenceRequest());
        existingDrug.setLisenceResponse(updatedDrug.getLisenceResponse());
        existingDrug.setTomojniRequest(updatedDrug.getTomojniRequest());
        existingDrug.setTomojniResponse(updatedDrug.getTomojniResponse());
        existingDrug.setTdsRequest(updatedDrug.getTdsRequest());
        existingDrug.setTdsResponse(updatedDrug.getTdsResponse());
        existingDrug.setBirzaRequest(updatedDrug.getBirzaRequest());
        existingDrug.setBirzaResponse(updatedDrug.getBirzaResponse());


        return drugRepository.save(existingDrug);
    }

    public List<Drug> filterDrugs(Long contractId, String name, String firma, Boolean isClosed, String contractNumber) {
        if (contractId == null) {
            throw new IllegalArgumentException("both of invoiceId and contractId cannot be null");
        }

        Specification<Drug> spec = Specification
                .where(DrugSpecifications.hasContractId(contractId))
                .and(DrugSpecifications.hasName(name))
                .and(DrugSpecifications.hasFirma(firma))
                .and(DrugSpecifications.hasContactNumber(contractNumber))
                .and(DrugSpecifications.hasClosedStatus(isClosed));

        return drugRepository.findAll(spec);
    }

    public List<DrugWithQuantityDTO> filterInvoiceDrugs(Long invoiceId, String name, String firma, Boolean isClosed) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("both of invoiceId and contractId cannot be null");
        }

        List<Long> drugIds = null;
        final Map<Long, Integer> quantityMap = new HashMap<>(); // mark final to avoid the warning


            List<DrugsAndInvoices> daiList = drugsAndInvoicesRepository.findByInvoiceId(invoiceId);
            drugIds = daiList.stream().map(DrugsAndInvoices::getDrugId).toList();
            daiList.forEach(dai -> quantityMap.put(dai.getDrugId(), dai.getQuantity()));


        Specification<Drug> spec = Specification
                .where(DrugSpecifications.hasIdIn(drugIds))
                .and(DrugSpecifications.hasName(name))
                .and(DrugSpecifications.hasFirma(firma))
                .and(DrugSpecifications.hasClosedStatus(isClosed));

        List<Drug> filteredDrugs = drugRepository.findAll(spec);

        return filteredDrugs.stream().map(drug -> {
            DrugWithQuantityDTO dto = new DrugWithQuantityDTO();
            dto.setId(drug.getId());
            dto.setName(drug.getName());
            dto.setFirma(drug.getFirma());
            dto.setProductionDate(drug.getProductionDate());
            dto.setExpireDate(drug.getExpireDate());
            dto.setSeriesNumber(drug.getSeriesNumber());
            dto.setPrice(drug.getPrice());
            dto.setAnalizeCertification(drug.getAnalizeCertification());
            dto.setQuantity(quantityMap.getOrDefault(drug.getId(), 0)); // override quantity
            return dto;
        }).collect(Collectors.toList());
    }

}

