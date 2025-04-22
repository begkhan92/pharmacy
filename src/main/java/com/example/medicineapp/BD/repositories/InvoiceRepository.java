package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
    List<Invoice> findByCargoId(Long cargoId);
}