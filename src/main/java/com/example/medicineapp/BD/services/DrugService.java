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

        System.out.println(drug.getIsClosed());
        drugRepository.save(drug);
    }

    public List<Drug> filterDrugs(String name, String manufacturer, Boolean isClosed) {
        Specification<Drug> spec = Specification
                .where(DrugSpecifications.hasName(name))
                .and(DrugSpecifications.hasManufacturer(manufacturer))
                .and(DrugSpecifications.hasClosedStatus(isClosed));

        return drugRepository.findAll(spec);
    }
}

