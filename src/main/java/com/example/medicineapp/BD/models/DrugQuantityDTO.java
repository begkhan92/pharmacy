package com.example.medicineapp.BD.models;

public class DrugQuantityDTO {
    private Long id;
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public DrugQuantityDTO() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
