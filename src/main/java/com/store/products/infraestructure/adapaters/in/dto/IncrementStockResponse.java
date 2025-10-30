package com.store.products.infraestructure.adapaters.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncrementStockResponse {
    private String message;
    private Long productId;
}
