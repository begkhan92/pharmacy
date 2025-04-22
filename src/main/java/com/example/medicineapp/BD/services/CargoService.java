package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.models.Invoice;
import com.example.medicineapp.BD.repositories.CargoRepository;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import com.example.medicineapp.BD.specifications.CargoSpecification;
import com.example.medicineapp.BD.specifications.DrugSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;
    private List<Invoice> invoices = new ArrayList<>();

    public void setSelectedInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Cargo> filterCargos(Integer year, Integer month) {


        Specification<Cargo> spec = Specification
                .where(CargoSpecification.hasDateArrivedInYearAndMonth(year, month)); // Ensure cargoId is checked
//                .and(DrugSpecifications.hasName(name))
//                .and(DrugSpecifications.hasFirma(firma))
//                .and(DrugSpecifications.hasContactNumber(contractNumber))
//                .and(DrugSpecifications.hasClosedStatus(isClosed));



        return cargoRepository.findAll(spec);
    }




    public List<Invoice> getSelectedInvoices() {
        return invoices;
    }
    public Cargo findById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cargo not found with ID: " + id));
    }
    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    public void saveCargo(Cargo cargo) {
        cargoRepository.save(cargo);
    }

}