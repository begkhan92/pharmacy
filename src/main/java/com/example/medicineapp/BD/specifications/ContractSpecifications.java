package com.example.medicineapp.BD.specifications;

import com.example.medicineapp.BD.models.Cargo;
import com.example.medicineapp.BD.models.Contract;
import com.example.medicineapp.BD.models.Drug;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.YearMonth;

public class ContractSpecifications {
    public static Specification<Contract> hasClosedStatus(Boolean isClosed) {
        return (root, query, criteriaBuilder) ->
                isClosed == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("closed"), isClosed);
    }

    public static Specification<Contract> hasDateArrivedInYearAndMonth(Integer year, Integer month) {
        return (root, query, criteriaBuilder) -> {
            if (year == null || month == null) {
                return criteriaBuilder.conjunction();
            }
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate startOfMonth = yearMonth.atDay(1);
            LocalDate endOfMonth = yearMonth.atEndOfMonth();
            return criteriaBuilder.between(root.get("arrivedAt"), startOfMonth, endOfMonth);
        };
    }
}
