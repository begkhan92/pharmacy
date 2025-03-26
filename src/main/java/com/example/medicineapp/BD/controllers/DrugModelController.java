package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.models.Firma;
import com.example.medicineapp.BD.services.DrugModelService;
import com.example.medicineapp.BD.services.FirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drug-models")
public class DrugModelController {

    @Autowired
    private DrugModelService drugModelService;
    @Autowired
    private FirmaService firmaService;


    @GetMapping
    public String listDrugModels(Model model) {
        List<DrugModel> drugModels = drugModelService.getAllDrugModels();
        model.addAttribute("drugModels", drugModels);
        return "drug-model-list";
    }

    @GetMapping("/add")
    public String showAddDrugModelForm(Model model) {

        List<Firma> firmas = firmaService.getAllFirmas();

        // Debugging Output
        System.out.println("Firmas found: " + firmas.size());

        model.addAttribute("drugModel", new DrugModel());
        model.addAttribute("firmas", firmas);
        return "add-drug-model";
    }

    @PostMapping("/save")
    public String saveDrug(@ModelAttribute("drug") DrugModel drug, @RequestParam("firma") Firma firma) {
//        Firma firma = firmaService.findById(firma.id); // Fetch Firma by ID
        drug.setFirma(firma);
        drugModelService.saveDrugModel(drug);
        return "redirect:/drugs";
    }

}