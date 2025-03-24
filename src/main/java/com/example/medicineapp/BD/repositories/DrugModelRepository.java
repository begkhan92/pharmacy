package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.DrugModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DrugModelRepository extends JpaRepository<DrugModel, Long>, JpaSpecificationExecutor<DrugModel> {
}
