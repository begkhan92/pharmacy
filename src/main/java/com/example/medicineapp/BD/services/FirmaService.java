package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.Firma;
import com.example.medicineapp.BD.repositories.FirmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirmaService {

    @Autowired
    private FirmaRepository firmaRepository;

    public List<Firma> getAllFirmas() {
        return firmaRepository.findAll();
    }

    public void saveFirma(Firma firma) {
        firmaRepository.save(firma);
    }

    public Firma findById(Long id) {
        return firmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Firma not found with ID: " + id));
    }

}