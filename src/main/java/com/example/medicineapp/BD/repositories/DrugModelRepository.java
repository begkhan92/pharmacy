package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DrugModelRepository extends JpaRepository<DrugModel, Long>, JpaSpecificationExecutor<DrugModel> {
    List<DrugModel> findByNameDoseContainingIgnoreCaseOrFirmaContainingIgnoreCase(String nameDose, String firma);
}