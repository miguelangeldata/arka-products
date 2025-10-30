package com.store.products.domain.ports.in;

import com.store.products.domain.models.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUsesCases {

    Mono<Product> createProduct(Product product);
    Mono<Product> getProductById(Long id);
    Mono<Product> updateProduct(Long productId,Product product);
    Flux<Product> getAllProducts();
    Mono<Void> deleteProduct(Long id);
    Mono<Void> incrementStock(Long productId, Integer quantity);
    Mono<Void>  decrementStock(Long productId, Integer quantity);
    Mono<Void> reserveStock(Long productId,Integer quantity);
    Mono<Integer> getAvailableStock(Long productId);
    Mono<Void> recoveryStock(Long productId,Integer quantity);
    Mono<Void> recoveryStockFromReturn(Long productId,Integer quantity);


}
