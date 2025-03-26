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

    public static Specification<Drug> hasManufacturer(String manufacturer) {
        return (root, query, criteriaBuilder) ->
                manufacturer == null || manufacturer.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("manufacturer"), "%" + manufacturer + "%");
    }

    public static Specification<Drug> hasClosedStatus(Boolean isClosed) {
        return (root, query, criteriaBuilder) ->
                isClosed == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("isClosed"), isClosed);
    }
}

