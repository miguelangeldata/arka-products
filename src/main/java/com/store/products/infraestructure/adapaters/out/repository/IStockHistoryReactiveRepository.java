package com.store.products.infraestructure.adapaters.out.repository;

import com.store.products.infraestructure.adapaters.out.entity.StockHistoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface IStockHistoryReactiveRepository extends ReactiveCrudRepository<StockHistoryEntity, UUID> {
    @Query("SELECT * FROM stock_history WHERE product_sku = :productSku")
    Flux<StockHistoryEntity> findAllStockHistoryByProductSku(@Param("productSku") String SKU);

}
