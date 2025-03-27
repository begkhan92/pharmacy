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

    public List<Drug> findByCargoId(Long cargoId) {
        return drugRepository.findByCargoId(cargoId);
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
        existingDrug.setProductionDate(updatedDrug.getProductionDate());
        existingDrug.setSeriesNumber(updatedDrug.getSeriesNumber());
        existingDrug.setPrice(updatedDrug.getPrice());
        existingDrug.setAnalizeCertification(updatedDrug.getAnalizeCertification());
        existingDrug.setExpireDate(updatedDrug.getExpireDate());


        drugRepository.save(existingDrug);
        return existingDrug;
    }

    public List<Drug> filterDrugs(Long cargoId, String name, String firma, Boolean isClosed, String contractNumber) {
        if (cargoId == null) {
            throw new IllegalArgumentException("cargoId cannot be null");
        }

        Specification<Drug> spec = Specification
                .where(DrugSpecifications.hasCargoId(cargoId)) // Ensure cargoId is checked
                .and(DrugSpecifications.hasName(name))
                .and(DrugSpecifications.hasFirma(firma))
                .and(DrugSpecifications.hasContactNumber(contractNumber))
                .and(DrugSpecifications.hasClosedStatus(isClosed));

        return drugRepository.findAll(spec);
    }

}

