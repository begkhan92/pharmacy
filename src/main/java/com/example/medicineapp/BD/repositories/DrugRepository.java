package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long>, JpaSpecificationExecutor<Drug> {
    List<Drug> findByInvoiceId(Long InvoiceId);
    List<Drug> findByContractId(Long contractId);

}

