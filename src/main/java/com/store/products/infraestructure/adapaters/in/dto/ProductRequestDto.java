package com.store.products.infraestructure.adapaters.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data required to create or update a product in the catalog.")
public class ProductRequestDto {

    @Schema(description = "Stock Keeping Unit (Unique product identifier).", example = "SKU-P001")
    private String sku;

    @Schema(description = "The display name of the product.", example = "Smartwatch X5")
    private String name;

    @Schema(description = "The product's category.", example = "Electronics")
    private String category;

    @Schema(description = "Detailed description of the product.", example = "High-performance smartwatch with health tracking features.")
    private String description;

    @Schema(description = "The selling price of the product.", example = "199.99")
    private Double price;

    @Schema(description = "The initial quantity of stock available.", example = "50")
    private Integer stock;

}
