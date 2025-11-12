package com.store.products.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductMetrics {
    private Long productsRegistered;
    private Long categories;

}
