package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugModelService {

    @Autowired
    private DrugModelRepository drugModelRepository;

    public List<DrugModel> getAllDrugModels() {
        return drugModelRepository.findAll();
    }

    public void saveDrugModel(DrugModel drugModel) {
        drugModelRepository.save(drugModel);
    }

    public DrugModel getDrugById(Long id) {
        return drugModelRepository.findById(id).orElseThrow(() -> new RuntimeException("Derman tapylmady"));
    }

    public DrugModel updateDrugModel(Long id, DrugModel updatedDrugModel) {
        DrugModel existingDrugModel = getDrugById(id);
        existingDrugModel.setNameDose(updatedDrugModel.getNameDose());
        existingDrugModel.setFirma(updatedDrugModel.getFirma());
        existingDrugModel.setUnit(updatedDrugModel.getUnit());


        drugModelRepository.save(existingDrugModel);
        return existingDrugModel;
    }

    public void deleteModelDrug(Long id) {
        drugModelRepository.deleteById(id);
    }

    public Optional<DrugModel> getDrugModelById(Long id){
        return drugModelRepository.findById(id);
    }

    public List<DrugModel> searchDrugModel(String name, String firma){
        return drugModelRepository.findByNameDoseContainingIgnoreCaseOrFirmaContainingIgnoreCase(name, firma);
    }

}