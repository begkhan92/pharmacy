package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import com.example.medicineapp.BD.services.CargoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/cargo")
public class CargoController {

    private final CargoService cargoService;
    private final DrugModelRepository drugModelRepository;

    public CargoController(CargoService cargoService, DrugModelRepository drugModelRepository) {
        this.cargoService = cargoService;
        this.drugModelRepository = drugModelRepository;
    }

    @GetMapping
    public String listCargos(Model model) {
        List<Cargo> cargos = cargoService.getAllCargo();
        model.addAttribute("cargos", cargos);
        return "cargo-list";
    }

    @GetMapping("/add-drugs")
    public String getDrugs(Model model) {
        List<DrugModel> drugs = drugModelRepository.findAll();
        model.addAttribute("drugs", drugs);
        return "drug-selection";
    }

    @PostMapping("/save")
    public String saveCargo(@ModelAttribute("cargo") Cargo cargo) {

        List<DrugModel> selectedDrugModels = cargoService.getSelectedDrugs();


        List<Drug> drugs = selectedDrugModels.stream().map(drugModel -> {
            Drug drug = new Drug();
            drug.setName(drugModel.getNameDose()); // Set name from DrugModel
            drug.setFirma(drugModel.getFirma()); // Set firma from DrugModel
            drug.setCargo(cargo); // Associate with Cargo
            return drug;
        }).toList();

        cargo.setDrugs(drugs);

        // Save Cargo (will also save Drugs due to cascade)
        cargoService.saveCargo(cargo);
        return "redirect:/cargo";
    }

    @GetMapping("/add-cargo")
    public String showAddCargoForm(Model model) {
        model.addAttribute("cargo", new Cargo());

        System.out.println(cargoService.getSelectedDrugs().size());
        return "add-cargo";
    }

    @PostMapping("/submit")
    public RedirectView submitDrugs(@RequestBody List<Long> selectedDrugIds) {
        System.out.println("Received Drug IDs: " + selectedDrugIds); // Debugging

        List<DrugModel> selectedDrugs = drugModelRepository.findAllById(selectedDrugIds);

        cargoService.setSelectedDrugs(selectedDrugs);

        return new RedirectView("/cargo/add-cargo");
    }


}
