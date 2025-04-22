package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.*;
import com.example.medicineapp.BD.repositories.DrugModelRepository;
import com.example.medicineapp.BD.repositories.DrugRepository;
import com.example.medicineapp.BD.repositories.InvoiceRepository;
import com.example.medicineapp.BD.services.CargoService;
import com.example.medicineapp.BD.services.ContractService;
import com.example.medicineapp.BD.services.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;
    private final ContractService contractService;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceService invoiceService;


    public CargoController(CargoService cargoService, ContractService contractService, DrugRepository drugRepository, DrugModelRepository drugModelRepository, InvoiceRepository invoiceRepository, InvoiceService invoiceService) {
        this.cargoService = cargoService;
        this.contractService = contractService;
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
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


    @GetMapping("/add-invoices")
    public String getDrugs(@RequestParam(required = false) Long cargoId, Model model) {
        List<Invoice> invoices = invoiceRepository.findAll();

        List<InvoiceDTO> invoiceDTOs = invoices.stream().map(i -> {
            InvoiceDTO dto = new InvoiceDTO();
            dto.setId(i.getId());
            dto.setNumber(i.getNumber());
            dto.setSelected(false);
            return dto;
        }).toList();

        if (cargoId != null) {
            List<Invoice> invoicesInCargo = invoiceRepository.findByCargoId(cargoId);

            for (InvoiceDTO dto : invoiceDTOs) {
                boolean exists = invoicesInCargo.stream().anyMatch(invoice ->
                        invoice.getId()== dto.getId() && invoice.getNumber() == dto.getNumber()
                );
                dto.setSelected(exists);
            }

            model.addAttribute("cargoId", cargoId);

        }

        model.addAttribute("invoices", invoiceDTOs);// Pass existing cargo drugs to the view

        return "selection-invoice";
    }

    @PostMapping("/submit")
    public RedirectView submitInvoices(@RequestBody List<Long> selectedInvoiceIds, @RequestParam(required = false) Long cargoId) {
        List<Invoice> selectedInvoices = invoiceRepository.findAllById(selectedInvoiceIds);

        for (Long id : selectedInvoiceIds){
//            Optional<Invoice> invoice = invoiceRepository.findById(id);
//            if()
            System.out.println("the id is:----"+id);
        }

        System.out.println("Form UI ids size:-----"+selectedInvoiceIds.size());

        System.out.println("Form UI Invoices size:-----"+selectedInvoices.size());

        cargoService.setSelectedInvoices(selectedInvoices);
        if(cargoId != null) return new RedirectView("/cargos/edit/" + cargoId);
        else return new RedirectView("/cargos/add-cargo");
    }

    @GetMapping("/add-cargo")
    public String showAddCargoForm(Model model) {
        model.addAttribute("cargo", new Cargo());
        return "add-cargo";
    }

    @GetMapping("/edit/{cargoId}")
    public String showEditDrugForm(@PathVariable Long cargoId, Model model) {
        List<Invoice> selectedInvoices = cargoService.getSelectedInvoices();
        Cargo cargo = cargoService.findById(cargoId);
        for (Invoice invoice : selectedInvoices) {
            boolean alreadyExists = cargo.getInvoices().stream().anyMatch(i ->
                    i.getId()==invoice.getId() &&
                            i.getNumber() == invoice.getNumber()
            );

            if (!alreadyExists) {
                cargo.addInvoice(invoice);
            }
        }
        model.addAttribute("cargo", cargo);
        model.addAttribute("cargoId", cargoId);
        return "edit-cargo";
    }

    @PostMapping("/save")
    public String saveCargo(@ModelAttribute("cargo") Cargo cargo) {

        List<Invoice> selectedInvoices = cargoService.getSelectedInvoices();

        System.out.println("Invoice Size-------"+selectedInvoices.size());
        cargoService.saveCargo(cargo);
        for (Invoice invoice : selectedInvoices) {
            boolean alreadyExists = cargo.getInvoices().stream().anyMatch(i ->
                    invoice.getId()==i.getId() &&
                            invoice.getNumber()==i.getNumber()
            );

            if (!alreadyExists) {// Optional, depending on cascade setup
                invoice.setCargo(cargo);
                invoiceService.saveInvoice(invoice);
            }
        }


        return "redirect:/cargos";
    }



  }
