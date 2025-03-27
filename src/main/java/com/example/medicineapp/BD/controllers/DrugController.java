package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.services.CargoService;
import com.example.medicineapp.BD.services.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drugs")
public class DrugController {

    @Autowired
    private DrugService drugService;
    @Autowired
    private CargoService cargoService;

    @GetMapping
    public String listDrugs(
            @RequestParam Long cargoId,  // Required cargoId
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String firma,
            @RequestParam(required = false) String contractNumber,
            @RequestParam(required = false) Boolean isClosed,
            Model model
    ) {
        List<Drug> drugs = drugService.filterDrugs(cargoId, name, firma, isClosed, contractNumber);
        model.addAttribute("drugs", drugs);
        model.addAttribute("cargoId", cargoId);
        return "drug-list";
    }



    @GetMapping("/add")
    public String showAddDrugForm(Model model, @RequestParam Long cargoId) {
        model.addAttribute("drug", new Drug());
        model.addAttribute("cargoId", cargoId);
        return "add-drug";
    }

    @PostMapping("/save")
    public String saveDrug(@ModelAttribute("drug") Drug drug, @RequestParam("cargoId") Long cargoId) {
        Cargo cargo = cargoService.findById(cargoId); // Fetch cargo from DB
        if (cargo == null) {
            System.out.println("Hic zat yok");
            throw new IllegalArgumentException("Invalid Cargo ID");

        }
        System.out.println(cargoId);
        drug.setCargo(cargo);
        drugService.saveDrug(drug);
        return "redirect:/drugs?cargoId=" + cargoId;
    }

    @GetMapping("/edit/{id}")
    public String showEditDrugForm(@PathVariable Long id, Model model) {
        Drug drug = drugService.getDrugById(id);
        model.addAttribute("drug", drug);
        return "edit-drug";
    }

    @PostMapping("/update/{id}")
    public String updateDrug(@PathVariable Long id, @ModelAttribute("drug") Drug updatedDrug) {
        Drug newDrug = drugService.updateDrug(id, updatedDrug);
        return "redirect:/drugs?cargoId=" + newDrug.getCargo().getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return "redirect:/drugs";
    }

    @GetMapping("/{id}")
    public String getDrugById(@PathVariable Long id, Model model) {
        Drug drug = drugService.getDrugById(id);
        model.addAttribute("drug", drug);
        return "drug-detail";
    }
}