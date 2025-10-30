package com.store.products.application.services;

import com.store.products.domain.models.StockHistory;
import com.store.products.domain.ports.in.StockHistoryUseCases;
import com.store.products.domain.ports.out.persistence.IStockHistoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockHistoryService implements StockHistoryUseCases {

    private final IStockHistoryPersistencePort persistence;
    @Override
    public Mono<StockHistory> registerStockHistory(StockHistory stockHistory) {
        return persistence.saveStockHistory(stockHistory);
    }

    @Override
    public Mono<StockHistory> getStockHistoryById(UUID id) {
        return persistence.findById(id);
    }

    @Override
    public Flux<StockHistory> getAllStockHistoryByProductSkU(String SKU) {
        return persistence.findAllStockHistoryByProductSku(SKU);
    }
}
