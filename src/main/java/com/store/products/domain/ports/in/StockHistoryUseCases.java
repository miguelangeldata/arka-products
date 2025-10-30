package com.store.products.domain.ports.in;

import com.store.products.domain.models.StockHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StockHistoryUseCases {
    Mono<StockHistory> registerStockHistory(StockHistory stockHistory);
    Mono<StockHistory> getStockHistoryById(UUID id);
    Flux<StockHistory> getAllStockHistoryByProductSkU(String SKU);
}
