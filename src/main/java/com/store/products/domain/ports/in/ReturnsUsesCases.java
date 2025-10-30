package com.store.products.domain.ports.in;

import com.store.products.domain.models.Returns;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReturnsUsesCases {
    Mono<Returns>createReturn(Returns returns);
    void acceptReturn(UUID id);
    void rejectReturn(UUID id);

    Mono<Returns>getById(UUID id);
    Flux<Returns> getAllByUserId(String userId);
    Flux<Returns> getAllReturnsByProductId(Long productId);
}
