package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}