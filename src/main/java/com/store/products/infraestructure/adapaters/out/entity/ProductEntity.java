package com.store.products.infraestructure.adapaters.out.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("products")
public class ProductEntity {

    @Id
    private Long id;
    private String sku;
    private String name;
    private String category;
    private String description;
    private Double price;
    @Column("total_stock")
    private Integer totalStock;
    @Column("reserved_stock")
    private Integer reservedStock;

}
