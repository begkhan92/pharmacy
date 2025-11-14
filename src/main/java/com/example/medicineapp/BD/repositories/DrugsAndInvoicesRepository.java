package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugsAndInvoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugsAndInvoicesRepository extends JpaRepository<DrugsAndInvoices, Long>, JpaSpecificationExecutor<DrugsAndInvoices> {
    List<DrugsAndInvoices> findByInvoiceId(Long InvoiceId);
}
