package com.store.products.domain.ports.out.persistence;

import com.store.products.domain.models.StockHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IStockHistoryPersistencePort {
    Mono<StockHistory> saveStockHistory(StockHistory stockHistory);
    Mono<StockHistory> findById(UUID id);
    Flux<StockHistory> findAllStockHistoryByProductSku(String SKU);
}
