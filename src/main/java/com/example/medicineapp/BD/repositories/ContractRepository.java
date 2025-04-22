package com.example.medicineapp.BD.repositories;

import com.example.medicineapp.BD.models.Contract;
import com.example.medicineapp.BD.models.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {


}