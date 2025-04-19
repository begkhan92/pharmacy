package com.example.medicineapp.BD.specifications;

import com.example.medicineapp.BD.models.Drug;
import org.springframework.data.jpa.domain.Specification;

public class DrugSpecifications {

    public static Specification<Drug> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null || name.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Drug> hasInvoiceId(Long invoiceId) {
        return (root, query, criteriaBuilder) ->
                invoiceId == null ? null : criteriaBuilder.equal(root.get("invoice").get("id"), invoiceId);
    }

    public static Specification<Drug> hasContractId(Long contractId) {
        return (root, query, criteriaBuilder) ->
                contractId == null ? null : criteriaBuilder.equal(root.get("contract").get("id"), contractId);
    }


    public static Specification<Drug> hasFirma(String firma) {
        return (root, query, criteriaBuilder) ->
                firma == null || firma.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("firma"), "%" + firma + "%");
    }

    public static Specification<Drug> hasContactNumber(String contractNumber) {
        return (root, query, criteriaBuilder) ->
                contractNumber == null || contractNumber.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("contractNumber"), "%" + contractNumber + "%");
    }

    public static Specification<Drug> hasClosedStatus(Boolean isClosed) {
        return (root, query, criteriaBuilder) ->
                isClosed == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("isClosed"), isClosed);
    }
}

