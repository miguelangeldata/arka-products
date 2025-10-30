package com.store.products.infraestructure.adapaters.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String sku;
    private String name;
    private String category;
    private String description;
    private Double price;
    private Integer stock;

}
