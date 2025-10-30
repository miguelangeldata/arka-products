package com.store.products.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String sku;
    private String name;
    private String category;
    private String description;
    private Double price;
    private Integer totalStock;
    private Integer reservedStock = 0;



    public Integer increaseStock(Integer quantity){
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return this.totalStock += quantity;
    }
    
    public Integer decreaseStock(Integer quantity){
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (this.reservedStock < quantity) {

            throw new IllegalStateException("Cannot decrease stock. Requested quantity (" + quantity +
                    ") exceeds the current reserved stock (" + this.reservedStock +
                    "). The order was likely not reserved.");
        }

        if (this.totalStock < quantity) {
            throw new IllegalStateException("Cannot decrease total stock below zero.");
        }
        this.reservedStock -= quantity;
        return this.totalStock -= quantity;
    
    }
    public Integer getAvailableStock(){
        return this.totalStock-this.reservedStock;
    }
    public void reserveStock(Integer quantity){
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (this.totalStock < quantity) {
            throw new IllegalArgumentException("Cannot decrease stock below zero");
        }
        this.reservedStock+=quantity;
    }
    public void recoverReservedStock(Integer quantity){
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (this.reservedStock < quantity) {
            throw new IllegalStateException("Cannot recover more stock than is currently reserved. Reserved: "
                    + this.reservedStock + ", Requested: " + quantity);
        }
        this.reservedStock -= quantity;
    }
}
