package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.Firma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FirmaRepository extends JpaRepository<Firma, Long>, JpaSpecificationExecutor<Firma> {
}