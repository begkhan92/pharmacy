package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Contract;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.repositories.ContractRepository;
import com.example.medicineapp.BD.specifications.CargoSpecification;
import com.example.medicineapp.BD.specifications.ContractSpecifications;
import com.example.medicineapp.BD.specifications.DrugSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;
    private List<DrugModel> drugs = new ArrayList<>();


    public void setSelectedDrugs(List<DrugModel> drugs) {
        this.drugs = drugs;
    }
    public List<DrugModel> getSelectedDrugs() {
        return drugs;
    }

    public List<Drug> getDrugsByContract(Long contractId) {
        return contractRepository.findById(contractId)
                .map(Contract::getDrugs)
                .orElse(Collections.emptyList());
    }

    public List<Contract> filterContracts(Integer year, Integer month) {

        Specification<Contract> spec = Specification
                .where(ContractSpecifications.hasDateArrivedInYearAndMonth(year, month));

        return contractRepository.findAll(spec);
    }

    public Contract findById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found with ID: " + id));
    }

    public List<Contract> getAllContract() {
        return contractRepository.findAll();
    }

    public void saveContract(Contract contract) {
        contractRepository.save(contract);
    }

    public Contract updateContract(Long id, Contract updatedContract) {
        Contract existingContract = findById(id);
//        existingContract.setName(updatedDrug.getName());
        existingContract.setActNumber(updatedContract.getActNumber());
        existingContract.setArrivedAt(updatedContract.getArrivedAt());
        existingContract.setNumberOfCars(updatedContract.getNumberOfCars());


        contractRepository.save(existingContract);
        return existingContract;
    }

    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }

}
