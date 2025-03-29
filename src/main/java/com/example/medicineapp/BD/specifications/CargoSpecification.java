package com.example.medicineapp.BD.specifications;

import com.example.medicineapp.BD.models.Cargo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class CargoSpecification {
    public static Specification<Cargo> hasDateArrivedInYear(Integer year) {
        return (root, query, criteriaBuilder) -> {
            if (year == null) {
                return criteriaBuilder.conjunction();
            }
            LocalDate startOfYear = Year.of(year).atDay(1);
            LocalDate endOfYear = Year.of(year).atMonth(12).atEndOfMonth();
            return criteriaBuilder.between(root.get("dateArrived"), startOfYear, endOfYear);
        };
    }

    public static Specification<Cargo> hasDateArrivedInYearAndMonth(Integer year, Integer month) {
        return (root, query, criteriaBuilder) -> {
            if (year == null || month == null) {
                return criteriaBuilder.conjunction();
            }
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate startOfMonth = yearMonth.atDay(1);
            LocalDate endOfMonth = yearMonth.atEndOfMonth();
            return criteriaBuilder.between(root.get("dateArrived"), startOfMonth, endOfMonth);
        };
    }
}
