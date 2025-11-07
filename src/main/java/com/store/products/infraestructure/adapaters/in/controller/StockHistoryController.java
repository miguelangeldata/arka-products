package com.store.products.infraestructure.adapaters.in.controller;

import com.store.products.application.services.StockHistoryService;
import com.store.products.domain.models.StockHistory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/stockHistory")
@RequiredArgsConstructor
@Tag(name = "Stock History", description = "Endpoints for consulting the history of stock movements.")
public class StockHistoryController {

    private final StockHistoryService service;

    @Operation(
            summary = "Get History by ID",
            description = "Searches for a specific stock movement record using its unique identifier (UUID).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "History record successfully found."),
                    @ApiResponse(responseCode = "404", description = "No history found with the provided ID.")
            }
    )
    @GetMapping("/{stockHistoryId}")
    public Mono<StockHistory> getByStockHistoryId(@PathVariable("stockHistoryId")UUID stockHistoryId){
        return service.getStockHistoryById(stockHistoryId);
    }
    @Operation(
            summary = "Get All History by SKU",
            description = "Retrieves a Flux of all stock movements performed for a specific product (SKU).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of stock movements (may be empty).")
            }
    )
    @GetMapping("/sku/{productSKU}")
    public Flux<StockHistory> getByProductSKU(@PathVariable("productSKU") String SKU){
        return service.getAllStockHistoryByProductSkU(SKU);
    }
}
