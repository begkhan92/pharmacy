package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DrugRepository extends JpaRepository<Drug, Long>, JpaSpecificationExecutor<Drug> {
}

