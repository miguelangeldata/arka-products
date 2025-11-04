package com.store.products.infraestructure.adapaters.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReturnItemEntity {
    @Column("product_id")
    private Long productId;
    @Column("quantity")
    private Integer quantity;
}
