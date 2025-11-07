package com.store.products.infraestructure.adapaters.in.controller;

import com.store.products.infraestructure.adapaters.in.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Management", description = "Reactive API for CRUD operations and stock management (inventory and reservation).")
public class ProductController {

    private final ProductUsesCases usesCases;
    private final ProductMapper mapper;

    @Operation(summary = "Create a new product", description = "Adds a new product to the catalog.")
    @ApiResponse(responseCode = "200", description = "Product created successfully.")
    @PostMapping
    public Mono<ProductResponseDto> createProduct(@Valid@RequestBody ProductRequestDto productRequestDto){
        return usesCases.createProduct(mapper.toDomain(productRequestDto)).map(mapper::toResponseDto);
    }

    @Operation(summary = "Retrieve product by ID", description = "Fetches details of a specific product.")
    @ApiResponse(responseCode = "200", description = "Product found and returned.")
    @ApiResponse(responseCode = "404", description = "Product not found.", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping("/{productId}")
    public Mono<ProductResponseDto> getProductById(@PathVariable Long productId){
        return usesCases.getProductById(productId).map(mapper::toResponseDto);
    }

    @Operation(summary = "Update an existing product", description = "Modifies details of a specific product.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully.")
    @ApiResponse(responseCode = "404", description = "Product not found.", content = @Content(schema = @Schema(hidden = true)))
    @PutMapping("/{productId}")
    public Mono<ProductResponseDto> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequestDto productRequestDto){
        return usesCases.updateProduct(productId, mapper.toDomain(productRequestDto)).map(mapper::toResponseDto);
    }

    @Operation(summary = "Retrieve all products", description = "Fetches a stream of all products in the catalog.")
    @ApiResponse(responseCode = "200", description = "Stream of products returned.")
    @GetMapping
    public Flux<ProductResponseDto> getAllProducts(){
        return usesCases.getAllProducts().map(mapper::toResponseDto);
    }
    @Operation(summary = "Delete product by ID", description = "Removes a product from the catalog.")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully (No Content).")
    @ApiResponse(responseCode = "404", description = "Product not found.", content = @Content(schema = @Schema(hidden = true)))
    @DeleteMapping("/{productId}")
    public Mono<Void> deleteProduct(@PathVariable Long productId){
        return usesCases.deleteProduct(productId);
    }
    @Operation(summary = "Increment product stock", description = "Adds a specified quantity to the product inventory.")
    @ApiResponse(responseCode = "200", description = "Stock successfully incremented.")
    @PostMapping ("/increment/{productId}")
    public Mono<IncrementStockResponse> incrementStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.incrementStock(productId, quantity)
                .then(Mono.just(new IncrementStockResponse("Stock was incremented successfully", productId)));
    }
    @Operation(summary = "Decrement product stock", description = "Subtracts a specified quantity from the product inventory.")
    @ApiResponse(responseCode = "200", description = "Stock successfully decremented.")
    @PostMapping ("/decrement/{productId}")
    public Mono<DecrementStockResponse> decrementStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.decrementStock(productId, quantity)
                .then(Mono.just(new DecrementStockResponse("Stock was decremented successfully", productId)));
    }

    @Operation(summary = "Reserve stock for an order", description = "Allocates a quantity of stock, reducing available inventory without finalizing shipment.")
    @ApiResponse(responseCode = "200", description = "Stock successfully reserved.")
    @ApiResponse(responseCode = "400", description = "Insufficient stock available for reservation.")
    @PostMapping("/reserve/{productId}")
    public Mono<ReservationResponse> reserveStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.reserveStock(productId, quantity)
                .then(Mono.just(new ReservationResponse(
                        String.format("Stock successfully reserved for product %d. Quantity: %d", productId, quantity),
                        productId,
                        quantity
                )));
    }

    @Operation(summary = "Recover reserved stock", description = "Releases reserved stock back into available inventory (e.g., if an order is cancelled).")
    @ApiResponse(responseCode = "200", description = "Stock successfully recovered.")
    @PutMapping("/recover/{productId}")
    public Mono<ReservationResponse> recoveryStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        return usesCases.recoveryStock(productId, quantity)
                .then(Mono.just(new ReservationResponse(
                        String.format(" Stock successfully recovered for product %d. Quantity: %d", productId, quantity),
                        productId,
                        quantity
                )));
    }
    @Operation(summary = "Check available stock quantity", description = "Returns the current quantity of stock available for immediate sale or reservation.")
    @ApiResponse(responseCode = "200", description = "Available stock quantity returned.")
    @GetMapping("/available/{productId}")
    public Mono<AvailableStockResponse> getAvailableStock(@PathVariable Long productId) {
        return usesCases.getAvailableStock(productId)
                .map(available -> new AvailableStockResponse(productId, available));
    }

}
