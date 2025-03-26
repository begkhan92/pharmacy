package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Drug;
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

    @GetMapping
    public String listDrugs(
            @RequestParam(required = false) Long cargoId,  // Add cargoId parameter
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) Boolean isClosed,
            Model model
    ) {
        List<Drug> drugs;
        if (cargoId != null) {
            drugs = drugService.findByCargoId(cargoId); // Fetch drugs for the given cargo
        } else {
            drugs = drugService.filterDrugs(name, manufacturer, isClosed);
        }
        model.addAttribute("drugs", drugs);
        return "drug-list";
    }


    @GetMapping("/add")
    public String showAddDrugForm(Model model) {
        model.addAttribute("drug", new Drug());
        return "add-drug";
    }

    @PostMapping("/save")
    public String saveDrug(@ModelAttribute("drug") Drug drug) {
        drugService.saveDrug(drug);
        return "redirect:/drugs";
    }

    @GetMapping("/edit/{id}")
    public String showEditDrugForm(@PathVariable Long id, Model model) {
        Drug drug = drugService.getDrugById(id);
        model.addAttribute("drug", drug);
        return "edit-drug";
    }

    @PostMapping("/update/{id}")
    public String updateDrug(@PathVariable Long id, @ModelAttribute("drug") Drug updatedDrug) {
        drugService.updateDrug(id, updatedDrug);
        return "redirect:/drugs";
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