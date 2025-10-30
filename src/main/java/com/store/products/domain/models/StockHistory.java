package com.store.products.domain.models;



import java.time.LocalDateTime;
import java.util.UUID;


public record StockHistory(
        UUID id, String productSKU, LocalDateTime createAt, Integer previousStock,
                           Integer newStock,StockHistoryReason reason) {
    public StockHistory(String productSKU, Integer previousStock, Integer newStock,StockHistoryReason reason) {
        this(null, productSKU, null, previousStock, newStock,reason);
    }


}
