package com.store.products.infraestructure.adapaters.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Full details of a product returned from the API.")
public class ProductResponseDto {

    @Schema(description = "Database ID of the product.", example = "101")
    private Long id;

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

    @Schema(description = "The current quantity of total stock available.", example = "60")
    private Integer stock;
}
