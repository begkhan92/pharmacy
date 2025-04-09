package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Drug;
import com.example.medicineapp.BD.models.Invoice;
import com.example.medicineapp.BD.repositories.DrugRepository;
import com.example.medicineapp.BD.services.CargoService;
import com.example.medicineapp.BD.services.ContractService;
import com.example.medicineapp.BD.services.DrugService;
import com.example.medicineapp.BD.services.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final CargoService cargoService;
    private final InvoiceService invoiceService;
    private final ContractService contractService;
    private final DrugService drugRepository;


    public InvoiceController(CargoService cargoService, InvoiceService invoiceService, ContractService contractService, DrugService drugRepository) {
        this.cargoService = cargoService;
        this.invoiceService = invoiceService;
        this.contractService = contractService;
        this.drugRepository = drugRepository;
    }

    @GetMapping
    public String listInvoices(Model model, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year){
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

        List<Invoice> invoices = invoiceService.filterInvoices(year, month);

        model.addAttribute("invoices", invoices);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("months", months);
        return "list-invoice";
    }

    @PostMapping("/submit")
    public RedirectView submitDrugs(
//            @RequestBody List<Long> selectedDrugIds
    ) {
//        System.out.println("Received Drug IDs: " + selectedDrugIds); // Debugging

//        List<Drug> selectedDrugs = drugRepository.findAllById(selectedDrugIds);

//        cargoService.setSelectedDrugs(selectedDrugs);

        return new RedirectView("/cargo/add-cargo");
    }

    @GetMapping("/add-invoice")
    public String showAddCargoForm(Model model) {
        model.addAttribute("invoice", new Invoice());

//        System.out.println(cargoService.getSelectedDrugs().size());
        return "add-invoice";
    }
    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute("invoice") Invoice invoice) {

//        List<Drug> selectedDrugModels = cargoService.getSelectedDrugs();
//
//        for (Drug drug : selectedDrugModels) {
//            drug = drugRepository.findById(drug.getId()).orElse(drug);
//            cargo.addDrug(drug);
//        }

        invoiceService.saveInvoice(invoice);
        return "redirect:/invoices";
    }
}
