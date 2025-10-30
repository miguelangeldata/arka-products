package com.store.products.domain.ports.out.persistence;

import com.store.products.domain.models.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {

    Mono<Product> save(Product product);
    Mono<Product> findById(Long id);
    Mono<Void> delete(Long id);
    Flux<Product> findAll();

}
