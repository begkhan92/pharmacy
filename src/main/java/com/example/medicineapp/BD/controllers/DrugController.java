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
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) Boolean isClosed,
            Model model
    ) {
        List<Drug> drugs = drugService.filterDrugs(name, manufacturer, isClosed);



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
}

