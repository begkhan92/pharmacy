package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.*;
import com.example.medicineapp.BD.repositories.DrugRepository;
import com.example.medicineapp.BD.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final CargoService cargoService;
    private final InvoiceService invoiceService;
    private final ContractService contractService;
    private final DrugService drugService;
    private final DrugRepository drugRepository;
    private final DrugsAndInvoicesService drugsAndInvoicesService;



    public InvoiceController(CargoService cargoService, InvoiceService invoiceService, ContractService contractService, DrugService drugService, DrugRepository drugRepository, DrugsAndInvoicesService drugsAndInvoicesService) {
        this.cargoService = cargoService;
        this.invoiceService = invoiceService;
        this.contractService = contractService;
        this.drugService = drugService;
        this.drugRepository = drugRepository;
        this.drugsAndInvoicesService = drugsAndInvoicesService;
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

    @GetMapping("/add-drugs")
    public String getDrugs(@RequestParam(required = false) Long invoiceId, Model model) {
        List<Drug> drugs = drugRepository.findAll();
        List<Contract> contracts = contractService.getAllContract();

        List<Drug> invoiceDrugs = new ArrayList<>();
        if (invoiceId != null) {
            invoiceDrugs = drugRepository.findByInvoiceId(invoiceId); // Fetch drugs in this cargo
        }

        model.addAttribute("drugs", drugs);
        model.addAttribute("contracts", contracts);
        model.addAttribute("invoiceDrugs", invoiceDrugs); // Pass existing cargo drugs to the view

        return "contract-selection";
    }

    @PostMapping("/save")
    public String saveInvoiceFromUI(@ModelAttribute("invoice") Invoice invoice) {
        List<Drug> selectedDrugs = invoiceService.getSelectedDrugs();
        List<DrugsAndInvoices> drugsAndInvoicesList = invoiceService.getDrugsAndInvoicesList();
//        invoice.setDrugs(selectedDrugs);
        System.out.println("--------------------------------------------"+invoice.getNumber());
        invoiceService.saveInvoice(invoice);
        for(DrugsAndInvoices drugs : drugsAndInvoicesList){
            drugs.setInvoiceId(invoice.getId());
            drugsAndInvoicesService.saveDrugsAndInvoices(drugs);
        }
        return "redirect:/invoices";
    }

    @GetMapping("/add-invoice")
    public String showAddInvoiceForm(Model model) {
        model.addAttribute("invoice", new Invoice());

        return "add-invoice";
    }
    //
    @PostMapping("/submit")
    public String submitInvoice(@RequestBody List<DrugQuantityDTO> selectedDrugs, Model model) {
        Map<Long, Integer> drugIdToQuantity = selectedDrugs.stream()
                .collect(Collectors.toMap(DrugQuantityDTO::getId, DrugQuantityDTO::getQuantity, Integer::sum));


        Invoice invoice = new Invoice();

        List<Drug> invoiceDrugs = new ArrayList<>();
        List<DrugsAndInvoices> drugsAndInvoices = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : drugIdToQuantity.entrySet()) {
            Long drugId = entry.getKey();
            Integer quantityToDeduct = entry.getValue();

            Drug originalDrug = drugRepository.findById(drugId)
                    .orElseThrow(() -> new RuntimeException("Drug not found: " + drugId));
            DrugsAndInvoices drugsAndInvoices1 = new DrugsAndInvoices();
            drugsAndInvoices1.setDrugId(originalDrug.getId());

            if (originalDrug.getQuantity() < quantityToDeduct) {
                throw new RuntimeException("Not enough quantity for drug ID " + drugId);
            }

            // Deduct quantity from original drug (optional, if drugs are "reserved")
            originalDrug.setQuantity(originalDrug.getQuantity() - quantityToDeduct);
            drugsAndInvoices1.setQuantity(quantityToDeduct);
            drugRepository.save(originalDrug);

            // Create drug for the invoice
            Drug invoiceDrug = new Drug();
            invoiceDrug.setName(originalDrug.getName());
            invoiceDrug.setFirma(originalDrug.getFirma());
            invoiceDrug.setQuantity(quantityToDeduct);
            invoiceDrug.setInvoice(invoice);

            invoiceDrugs.add(invoiceDrug);
            drugsAndInvoices.add(drugsAndInvoices1);
        }

//        invoice.setDrugs(invoiceDrugs);
        invoiceService.setSelectedDrugs(invoiceDrugs);
//        invoiceService.saveInvoice(invoice);
        invoiceService.setDrugsAndInvoicesList(drugsAndInvoices);
//        for(DrugsAndInvoices drugs : drugsAndInvoices){
//            drugs.setInvoiceId(invoice.getId());
//            drugsAndInvoicesService.saveDrugsAndInvoices(drugs);
//        }


        return "redirect:/invoices/add-invoice";
    }




}
