package com.store.products.infraestructure.adapaters.out.entity;

import com.store.products.domain.models.StockHistoryReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_history")
public class StockHistoryEntity {
    @Id
    private UUID id;
    @Column("product_sku")
    private String productSKU;
    @Column("create_at")
    private LocalDateTime createAt;
    @Column("previous_stock")
    private Integer previousStock;
    @Column("new_stock")
    private Integer newStock;
    private StockHistoryReason reason;
}
