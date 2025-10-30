package com.store.products.infraestructure.adapaters.out.persistence;

import com.store.products.domain.models.StockHistory;
import com.store.products.domain.ports.out.persistence.IStockHistoryPersistencePort;
import com.store.products.infraestructure.adapaters.out.repository.IStockHistoryReactiveRepository;
import com.store.products.infraestructure.mapper.StockHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Component
@RequiredArgsConstructor
public class StockHistoryPersistenceAdapter implements IStockHistoryPersistencePort {
    private final StockHistoryMapper mapper;
    private final IStockHistoryReactiveRepository repository;
    @Override
    public Mono<StockHistory> saveStockHistory(StockHistory stockHistory) {
        return repository.save(mapper.stockHistoryToEntity(stockHistory)).map(mapper::stockHistoryEntityToDomain);
    }
    @Override
    public Mono<StockHistory> findById(UUID id) {
       return repository.findById(id).map(mapper::stockHistoryEntityToDomain);
    }

    @Override
    public Flux<StockHistory> findAllStockHistoryByProductSku(String SKU) {
        return repository.findAllStockHistoryByProductSku(SKU).map(mapper::stockHistoryEntityToDomain);
    }
}
