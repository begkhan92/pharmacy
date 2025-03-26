package com.example.medicineapp.BD.controllers;

import com.example.medicineapp.BD.models.Firma;
import com.example.medicineapp.BD.services.FirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/firma")
public class FirmaController {

    @Autowired
    private FirmaService firmaService;


    @GetMapping
    public String listDrugModels(Model model) {
        List<Firma> firmas = firmaService.getAllFirmas();
        model.addAttribute("firmas", firmas);
        return "firma-list";
    }

    @GetMapping("/add")
    public String showAddFirmaForm(Model model) {
        model.addAttribute("firma", new Firma());
        return "add-firma";
    }

    @PostMapping("/save")
    public String saveFirma(@ModelAttribute("firma") Firma firma) {
        firmaService.saveFirma(firma);
        return "redirect:/firma";
    }
}