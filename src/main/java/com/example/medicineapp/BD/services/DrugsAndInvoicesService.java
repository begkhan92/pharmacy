package com.example.medicineapp.BD.services;

import com.example.medicineapp.BD.models.DrugsAndInvoices;
import com.example.medicineapp.BD.repositories.DrugsAndInvoicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugsAndInvoicesService {

    @Autowired
    private DrugsAndInvoicesRepository drugsAndInvoicesRepository;

    public void saveDrugsAndInvoices(DrugsAndInvoices drugsAndInvoices) {
        drugsAndInvoicesRepository.save(drugsAndInvoices);
    }

}
