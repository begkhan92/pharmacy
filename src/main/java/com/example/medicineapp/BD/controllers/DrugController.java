package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.*;
import com.example.medicineapp.BD.services.*;
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
    private InvoiceService invoiceService;
    @Autowired
    private ContractService contractService;

    @GetMapping
    public String listDrugs(
            @RequestParam(required = false) Long invoiceId,
            @RequestParam(required = false) Long contractId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String firma,
            @RequestParam(required = false) String contractNumber,
            @RequestParam(required = false) Boolean isClosed,
            Model model
    ) {
        List<Drug> drugs = drugService.filterDrugs(contractId, name, firma, isClosed, contractNumber);

        model.addAttribute("drugs", drugs);
        model.addAttribute("invoiceId", invoiceId);
        model.addAttribute("contractId", contractId);
        return "drug-list";
    }

    @GetMapping("/list-invoice-drugs")
    public String listDrugsFromInvoice(
            @RequestParam Long invoiceId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String firma,
            @RequestParam(required = false) Boolean isClosed,
            Model model
    ) {
        List<DrugWithQuantityDTO> drugs = drugService.filterInvoiceDrugs(invoiceId, name, firma, isClosed);
        model.addAttribute("drugs", drugs);
        model.addAttribute("invoiceId", invoiceId);
        return "list-invoice-drugs";
    }



    @GetMapping("/add")
    public String showAddDrugForm(Model model, @RequestParam(required = false) Long invoiceId, @RequestParam(required = false) Long contractId) {
        model.addAttribute("drug", new Drug());
        model.addAttribute("invoiceId", invoiceId);
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
    public String saveDrug(@ModelAttribute("drug") Drug drug, @RequestParam(value = "invoiceId", required = false) Long invoiceId, @RequestParam(value = "contractId", required = false) Long contractId,  @RequestParam("drugId") Long drugModelId) {


        DrugModel selectedDrugModel = drugModelService.getDrugModelById(drugModelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid DrugModel ID"));


        drug.setName(selectedDrugModel.getNameDose());
        drug.setFirma(selectedDrugModel.getFirma());

        if (invoiceId != null) {
            Invoice invoice = invoiceService.findById(invoiceId);
            invoice.addDrug(drug);

        } else if(contractId != null) {
            Contract contract = contractService.findById(contractId);
            contract.addDrug(drug);
        } else {
            System.out.println("Hic zat yok");
            throw new IllegalArgumentException("Invalid ID");
        }


        drugService.saveDrug(drug);
        String finalEndPoint="";
        if(invoiceId != null) finalEndPoint = "redirect:/drugs?invoiceId=" + invoiceId; else finalEndPoint = "redirect:/drugs?contractId=" + contractId;
        return finalEndPoint;
    }

    @GetMapping("/edit/{id}")
    public String showEditDrugForm(@PathVariable Long id, Model model) {
        Drug drug = drugService.getDrugById(id);
        model.addAttribute("drug", drug);
        model.addAttribute("contractId", drug.getContract().getId());
        return "edit-drug";
    }

    @PostMapping("/update/{id}")
    public String updateDrug(@PathVariable Long id, @ModelAttribute("drug") Drug updatedDrug, @RequestParam(required = false) Long contractId, @RequestParam(required = false) Long invoiceId) {

        Drug newDrug = drugService.updateDrug(id, updatedDrug);
        if(invoiceId != null)
            return "redirect:/drugs?invoiceId=" + invoiceId;
        else
            return "redirect:/drugs?contractId=" + contractId;
    }

    @GetMapping("/delete/{id}")
    public String deleteDrug(@PathVariable Long id, @RequestParam(required = false) Long contractId) {
        drugService.deleteDrug(id);
        return "redirect:/drugs?contractId=" + contractId;
    }

    @GetMapping("/{id}")
    public String getDrugById(@PathVariable Long id, Model model) {
        Drug drug = drugService.getDrugById(id);
        model.addAttribute("drug", drug);
        return "drug-detail";
    }
}