package com.store.products.infraestructure.adapaters.out.persistence;

import com.store.products.domain.models.Returns;
import com.store.products.domain.ports.out.persistence.ReturnsPersistencePort;
import com.store.products.infraestructure.adapaters.out.repository.ReturnsReactiveRepository;
import com.store.products.infraestructure.mapper.ReturnsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReturnPersistenceAdapter implements ReturnsPersistencePort {
    private final ReturnsMapper mapper;
    private final ReturnsReactiveRepository repository;

    @Override
    public Mono<Returns> save(Returns returns) {
        return repository.save(mapper.domainToEntity(returns))
                .map(mapper::entityToDomain);
    }

    @Override
    public Mono<Returns> getById(UUID id) {
        return repository.findById(id).map(mapper::entityToDomain);
    }

    @Override
    public Flux<Returns> getReturnsByProductId(Long productId) {
        return repository.getAllReturnsByProductId(productId).map(mapper::entityToDomain);
    }

    @Override
    public Flux<Returns> getReturnsByUserId(String userId) {
        return repository.getAllReturnsByUserId(userId).map(mapper::entityToDomain);
    }
}
