package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.*;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import com.example.medicineapp.BD.repositories.DrugRepository;
import com.example.medicineapp.BD.services.CargoService;
import com.example.medicineapp.BD.services.ContractService;
import com.example.medicineapp.BD.services.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private final ContractService contractService;
    private final DrugModelRepository drugModelRepository;
    private final DrugRepository drugRepository;
    private final DrugService drugService;

    public ContractController(ContractService contractService, DrugModelRepository drugModelRepository, DrugRepository drugRepository, DrugService drugService) {
        this.contractService = contractService;
        this.drugModelRepository = drugModelRepository;
        this.drugRepository = drugRepository;
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
    public String getDrugs(@RequestParam(required = false) Long contractId, Model model) {
        List<DrugModel> drugModels = drugModelRepository.findAll();

        List<DrugModelDTO> drugDTOs = drugModels.stream().map(dm -> {
            DrugModelDTO dto = new DrugModelDTO();
            dto.setId(dm.getId());
            dto.setNameDose(dm.getNameDose());
            dto.setFirma(dm.getFirma());
            dto.setSelected(false); // default

            return dto;
        }).toList();

        if (contractId != null) {
            List<Drug> drugsInContract = drugRepository.findByContractId(contractId);

            // Flag DTOs that already exist in this contract
            for (DrugModelDTO dto : drugDTOs) {
                boolean exists = drugsInContract.stream().anyMatch(drug ->
                        drug.getName().equals(dto.getNameDose()) &&
                                drug.getFirma().equals(dto.getFirma())
                );
                dto.setSelected(exists);
            }

            model.addAttribute("contractId", contractId);
        }

        model.addAttribute("drugs", drugDTOs);
        return "drug-selection";
    }

    @PostMapping("/submit")
    public RedirectView submitDrugs(@RequestBody List<Long> selectedDrugIds, @RequestParam(required = false) Long contractId) {
        System.out.println("Received Drug IDs: " + selectedDrugIds); // Debugging

        List<DrugModel> selectedDrugs = drugModelRepository.findAllById(selectedDrugIds);


        contractService.setSelectedDrugs(selectedDrugs);

        System.out.println(" Kontract --- -- -- -- -- -- - -  "+contractId);

        if(contractId != null) return new RedirectView("/contracts/edit/" + contractId);

        else

            return new RedirectView("/contracts/add-contract");
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
            boolean alreadyExists = contract.getDrugs().stream().anyMatch(drug ->
                    drug.getName().equals(drugModel.getNameDose()) &&
                            drug.getFirma().equals(drugModel.getFirma())
            );

            if (!alreadyExists) {
                Drug drug = new Drug();
                drug.setName(drugModel.getNameDose());
                drug.setFirma(drugModel.getFirma());
                drug.setContract(contract); // Optional, depending on cascade setup
                contract.addDrug(drug);     // ✅ Keep bidirectional link
            }
        }
        contractService.saveContract(contract);
        return "redirect:/contracts";
    }
    @GetMapping("/edit/{contractId}")
    public String showEditDrugForm(@PathVariable Long contractId, Model model) {
        List<DrugModel> selectedDrugModels = contractService.getSelectedDrugs();
        Contract contract = contractService.findById(contractId);
        System.out.println(selectedDrugModels.size());
        for (DrugModel drugModel : selectedDrugModels) {
            boolean alreadyExists = contract.getDrugs().stream().anyMatch(drug ->
                    drug.getName().equals(drugModel.getNameDose()) &&
                            drug.getFirma().equals(drugModel.getFirma())
            );

            if (!alreadyExists) {
                Drug drug = new Drug();
                drug.setName(drugModel.getNameDose());
                drug.setFirma(drugModel.getFirma());
                contract.addDrug(drug);
                drugRepository.save(drug);
            }
        }
        System.out.println("drugs in contract - " + contract.getDrugs().size());
        model.addAttribute("contract", contract);
        model.addAttribute("contractId", contractId);
        return "edit-contract";
    }


    @PostMapping("/update/{id}")
    public String updateContract(@PathVariable Long id, @ModelAttribute("contract") Contract updatedContract) {
        Contract newContract = contractService.updateContract(id, updatedContract);
        return "redirect:/contracts";
    }


}
