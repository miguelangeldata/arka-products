package com.store.products.infraestructure.adapaters.in.controller;

import com.store.products.infraestructure.adapaters.in.dto.*;
import org.springframework.web.bind.annotation.*;

import com.store.products.domain.ports.in.ProductUsesCases;
import com.store.products.infraestructure.mapper.ProductMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUsesCases usesCases;
    private final ProductMapper mapper;

    @PostMapping
    public Mono<ProductResponseDto> createProduct(@Valid@RequestBody ProductRequestDto productRequestDto){
        return usesCases.createProduct(mapper.toDomain(productRequestDto)).map(mapper::toResponseDto);
    }
    @GetMapping("/{productId}")
    public Mono<ProductResponseDto> getProductById(@PathVariable Long productId){
        return usesCases.getProductById(productId).map(mapper::toResponseDto);
    }
    @PutMapping("/{productId}")
    public Mono<ProductResponseDto> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequestDto productRequestDto){
        return usesCases.updateProduct(productId, mapper.toDomain(productRequestDto)).map(mapper::toResponseDto);
    }
    @GetMapping
    public Flux<ProductResponseDto> getAllProducts(){
        return usesCases.getAllProducts().map(mapper::toResponseDto);
    }
    @DeleteMapping("/{productId}")
    public Mono<Void> deleteProduct(@PathVariable Long productId){
        return usesCases.deleteProduct(productId);
    }
    @PostMapping ("/increment/{productId}")
    public Mono<IncrementStockResponse> incrementStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.incrementStock(productId, quantity)
                .then(Mono.just(new IncrementStockResponse("Stock was incremented successfully", productId)));
    }

    @PostMapping ("/decrement/{productId}")
    public Mono<DecrementStockResponse> decrementStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.decrementStock(productId, quantity)
                .then(Mono.just(new DecrementStockResponse("Stock was decremented successfully", productId)));
    }

    @PostMapping("/reserve/{productId}")
    public Mono<ReservationResponse> reserveStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.reserveStock(productId, quantity)
                .then(Mono.just(new ReservationResponse(
                        String.format("Stock successfully reserved for product %d. Quantity: %d", productId, quantity),
                        productId,
                        quantity
                )));
    }

    @PutMapping("/recover/{productId}")
    public Mono<ReservationResponse> recoveryStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.recoveryStock(productId, quantity)
                .then(Mono.just(new ReservationResponse(
                        String.format(" Stock successfully recovered for product %d. Quantity: %d", productId, quantity),
                        productId,
                        quantity
                )));
    }

    @GetMapping("/available/{productId}")
    public Mono<AvailableStockResponse> getAvailableStock(@PathVariable Long productId) {
        return usesCases.getAvailableStock(productId)
                .map(available -> new AvailableStockResponse(productId, available));
    }

}
