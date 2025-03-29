package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import com.example.medicineapp.BD.services.CargoService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public String listCargos(Model model, @RequestParam(required = false) Integer month,@RequestParam(required = false) Integer year){
        if (year == null) year = LocalDate.now().getYear();
        if(month == null) month = LocalDate.now().getMonthValue();

        Map<Integer, String> months = new LinkedHashMap<>();
        months.put(1, "Ýanwar");
        months.put(2, "Fewral");
        months.put(3, "Mart");
        months.put(4, "Aprel");
        months.put(5, "Maý");
        months.put(6, "Iýun");
        months.put(7, "Iýul");
        months.put(8, "Awgust");
        months.put(9, "Sentýabr");
        months.put(10, "Oktýabr");
        months.put(11, "Noýabr");
        months.put(12, "Dekabr");

        List<Cargo> cargos = cargoService.filterCargos(year, month);

        model.addAttribute("cargos", cargos);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("months", months);
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
