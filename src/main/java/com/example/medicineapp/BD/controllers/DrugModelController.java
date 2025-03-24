package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.services.DrugModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/save")
    public String saveDrugModel(@ModelAttribute("drugModel") DrugModel drugModel) {
        drugModelService.saveDrugModel(drugModel);
        return "redirect:/drug-models";
    }
}
