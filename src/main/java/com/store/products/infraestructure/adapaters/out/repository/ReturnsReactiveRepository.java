package com.store.products.infraestructure.adapaters.out.repository;

import com.store.products.infraestructure.adapaters.out.entity.ReturnsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ReturnsReactiveRepository extends ReactiveCrudRepository<ReturnsEntity, UUID> {
    Flux<ReturnsEntity> getAllReturnsByProductId(Long productId);
    Flux<ReturnsEntity> getAllReturnsByUserId(String userId);
}
