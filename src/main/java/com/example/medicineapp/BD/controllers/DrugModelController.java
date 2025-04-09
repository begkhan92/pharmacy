package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.services.DrugModelService;
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


    @GetMapping
    public String listDrugModels(Model model) {
        List<DrugModel> drugModels = drugModelService.getAllDrugModels();
        model.addAttribute("drugModels", drugModels);
        return "drug-model-list";
    }

    @GetMapping("/add")
    public String showAddDrugModelForm(Model model) {



        model.addAttribute("drugModel", new DrugModel());
        return "add-drug-model";
    }

    @GetMapping("/edit/{id}")
    public String showEditDrugModelForm(@PathVariable Long id, Model model) {
        DrugModel drugModel = drugModelService.getDrugById(id);
        model.addAttribute("drugModel", drugModel);
        return "edit-drug-model";
    }

    @PostMapping("/update/{id}")
    public String updateDrugModel(@PathVariable Long id, @ModelAttribute("drugModel") DrugModel updatedDrugModel) {
        DrugModel newDrugModel =
                drugModelService.updateDrugModel(id, updatedDrugModel);
        return "redirect:/drug-models";
    }

    @PostMapping("/save")
    public String saveDrug(@ModelAttribute("drug") DrugModel drug, @RequestParam("firma") String firma) {
//        Firma firma = firmaService.findById(firma.id); // Fetch Firma by ID
        drug.setFirma(firma);
        drugModelService.saveDrugModel(drug);
        return "redirect:/drug-models/add";
    }

}