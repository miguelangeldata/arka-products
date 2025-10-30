package com.store.products.domain.ports.out.persistence;

import com.store.products.domain.models.Returns;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReturnsPersistencePort {
    Mono<Returns> save(Returns returns);
    Mono<Returns> getById(UUID id);
    Flux<Returns> getReturnsByProductId(Long productId);
    Flux<Returns> getReturnsByUserId(String userId);
}
