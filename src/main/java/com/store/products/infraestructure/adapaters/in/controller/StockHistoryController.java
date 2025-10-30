package com.store.products.infraestructure.adapaters.in.controller;

import com.store.products.application.services.StockHistoryService;
import com.store.products.domain.models.StockHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/stockHistory")
@RequiredArgsConstructor
public class StockHistoryController {

    private final StockHistoryService service;

    @GetMapping("/{stockHistoryId}")
    public Mono<StockHistory> getByStockHistoryId(@PathVariable("stockHistoryId")UUID stockHistoryId){
        return service.getStockHistoryById(stockHistoryId);
    }
    @GetMapping("/sku/{productSKU}")
    public Flux<StockHistory> getByProductSKU(@PathVariable("productSKU") String SKU){
        return service.getAllStockHistoryByProductSkU(SKU);
    }
}
