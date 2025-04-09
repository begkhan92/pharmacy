package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.*;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import com.example.medicineapp.BD.services.CargoService;
import com.example.medicineapp.BD.services.ContractService;
import com.example.medicineapp.BD.services.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private final ContractService contractService;
    private final DrugModelRepository drugModelRepository;
    private final DrugService drugService;

    public ContractController(ContractService contractService, DrugModelRepository drugModelRepository, DrugService drugService) {
        this.contractService = contractService;
        this.drugModelRepository = drugModelRepository;
        this.drugService = drugService;
    }
    @GetMapping
    public String listContracts(Model model){
        List<Contract> contracts = contractService.getAllContract();
        model.addAttribute("contracts", contracts);
        return "contract-list";
    }

    @GetMapping("/{contractId}/drugs")
    @ResponseBody
    public List<Drug> getDrugsByContract(@PathVariable Long contractId) {
        return contractService.getDrugsByContract(contractId);
    }

//    @GetMapping("/{contractId}/drugs")
//    @ResponseBody
//    public List<DrugDTO> getDrugsByContract(@PathVariable Long contractId) {
//        List<Drug> drugs = contractService.getDrugsByContract(contractId);
//        return drugs.stream()
//                .map(DrugDTO::new)
//                .collect(Collectors.toList());
//    }



    @GetMapping("/add-drugs")
    public String getDrugs(Model model) {
        List<DrugModel> drugs = drugModelRepository.findAll();
        model.addAttribute("drugs", drugs);
        return "drug-selection";
    }

    @PostMapping("/submit")
    public RedirectView submitDrugs(@RequestBody List<Long> selectedDrugIds) {
        System.out.println("Received Drug IDs: " + selectedDrugIds); // Debugging

        List<DrugModel> selectedDrugs = drugModelRepository.findAllById(selectedDrugIds);

        contractService.setSelectedDrugs(selectedDrugs);

        return new RedirectView("/contract/add-contract");
    }
    @GetMapping("/add-contract")
    public String showAddCargoForm(Model model) {
        model.addAttribute("contract", new Contract());

        System.out.println(contractService.getSelectedDrugs().size());
        return "add-contract";
    }

    @PostMapping("/save")
    public String saveContract(@ModelAttribute("contract") Contract contract) {

        List<DrugModel> selectedDrugModels = contractService.getSelectedDrugs();


        for (DrugModel drugModel : selectedDrugModels) {
            Drug drug = new Drug();
            drug.setName(drugModel.getNameDose());
            drug.setFirma(drugModel.getFirma());
            contract.addDrug(drug);  // ✅ Use addDrug to maintain bidirectional relationship
        }




        // Save Cargo (will also save Drugs due to cascade)
        contractService.saveContract(contract);
        return "redirect:/contract";
    }
    @GetMapping("/edit/{id}")
    public String showEditDrugForm(@PathVariable Long id, Model model) {
        Contract contract = contractService.findById(id);
        model.addAttribute("contract", contract);
        return "edit-contract";
    }
    @PostMapping("/update/{id}")
    public String updateContract(@PathVariable Long id, @ModelAttribute("contract") Contract updatedContract) {
        Contract newContract = contractService.updateContract(id, updatedContract);
        return "redirect:/contract";
    }


}
