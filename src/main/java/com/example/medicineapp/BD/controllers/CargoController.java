package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Contract;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import com.example.medicineapp.BD.repositories.DrugRepository;
import com.example.medicineapp.BD.services.CargoService;
import com.example.medicineapp.BD.services.ContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;
    private final ContractService contractService;
    private final DrugRepository drugRepository;


    public CargoController(CargoService cargoService, ContractService contractService, DrugRepository drugRepository, DrugModelRepository drugModelRepository) {
        this.cargoService = cargoService;
        this.contractService = contractService;
        this.drugRepository = drugRepository;
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
        return "list-cargo";
    }



    @GetMapping("/add-drugs")
    public String getDrugs(@RequestParam(required = false) Long cargoId, Model model) {
        List<Drug> drugs = drugRepository.findAll();
        List<Contract> contracts = contractService.getAllContract();

        List<Drug> cargoDrugs = new ArrayList<>();
        if (cargoId != null) {
            cargoDrugs = drugRepository.findByCargoId(cargoId); // Fetch drugs in this cargo
        }

        model.addAttribute("drugs", drugs);
        model.addAttribute("contracts", contracts);
        model.addAttribute("cargoDrugs", cargoDrugs); // Pass existing cargo drugs to the view

        return "contract-selection";
    }


    @PostMapping("/save")
    public String saveCargo(@ModelAttribute("cargo") Cargo cargo) {

        List<Drug> selectedDrugModels = cargoService.getSelectedDrugs();

        for (Drug drug : selectedDrugModels) {
            drug = drugRepository.findById(drug.getId()).orElse(drug);
            cargo.addDrug(drug);
        }

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

        List<Drug> selectedDrugs = drugRepository.findAllById(selectedDrugIds);

        cargoService.setSelectedDrugs(selectedDrugs);

        return new RedirectView("/cargo/add-cargo");
    }


}
