package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Contract;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.DrugModel;
import com.example.medicineapp.BD.services.CargoService;
import com.example.medicineapp.BD.services.ContractService;
import com.example.medicineapp.BD.services.DrugModelService;
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
    private DrugModelService drugModelService;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private ContractService contractService;

    @GetMapping
    public String listDrugs(
            @RequestParam(required = false) Long cargoId,
            @RequestParam(required = false) Long contractId,// Required cargoId
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String firma,
            @RequestParam(required = false) String contractNumber,
            @RequestParam(required = false) Boolean isClosed,
            Model model
    ) {
        List<Drug> drugs = drugService.filterDrugs(cargoId,contractId, name, firma, isClosed, contractNumber);
        model.addAttribute("drugs", drugs);
        model.addAttribute("cargoId", cargoId);
        model.addAttribute("contractId", contractId);
        return "drug-list";
    }



    @GetMapping("/add")
    public String showAddDrugForm(Model model, @RequestParam(required = false) Long cargoId, @RequestParam(required = false) Long contractId) {
        model.addAttribute("drug", new Drug());
        model.addAttribute("cargoId", cargoId);
        model.addAttribute("contractId", contractId);
        List<DrugModel> drugModels = drugModelService.getAllDrugModels();
        model.addAttribute("drugModels", drugModels);
        return "add-drug";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<DrugModel> searchDrugs(@RequestParam String query) {

        return drugModelService.searchDrugModel(query, query);
    }

    @PostMapping("/save")
    public String saveDrug(@ModelAttribute("drug") Drug drug, @RequestParam(value = "cargoId", required = false) Long cargoId, @RequestParam(value = "contractId", required = false) Long contractId,  @RequestParam("drugId") Long drugModelId) {


        DrugModel selectedDrugModel = drugModelService.getDrugModelById(drugModelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid DrugModel ID"));


        drug.setName(selectedDrugModel.getNameDose());
        drug.setFirma(selectedDrugModel.getFirma());

        if (cargoId != null) {
            Cargo cargo = cargoService.findById(cargoId);
            cargo.addDrug(drug);

        } else if(contractId != null) {
            Contract contract = contractService.findById(contractId);// Fetch cargo from DB
            contract.addDrug(drug);
        } else {
            System.out.println("Hic zat yok");
            throw new IllegalArgumentException("Invalid Cargo ID");
        }


        drugService.saveDrug(drug);
        String finalEndPoint="";
        if(cargoId != null) finalEndPoint = "redirect:/drugs?cargoId=" + cargoId; else finalEndPoint = "redirect:/drugs?contractId=" + contractId;
        return finalEndPoint;
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