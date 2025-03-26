package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.repositories.CargoRepository;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;
    private List<DrugModel> drugs = new ArrayList<>();

    public void setSelectedDrugs(List<DrugModel> drugs) {
        this.drugs = drugs;
    }

    public List<DrugModel> getSelectedDrugs() {
        return drugs;
    }

    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    public void saveCargo(Cargo cargo) {
        cargoRepository.save(cargo);
    }

}