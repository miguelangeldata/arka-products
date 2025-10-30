package com.store.products.infraestructure.adapaters.out.persistence;

import org.springframework.stereotype.Component;

import com.store.products.domain.models.Product;
import com.store.products.domain.ports.out.persistence.IProductPersistencePort;
import com.store.products.infraestructure.adapaters.out.repository.SpringReactiveRepository;
import com.store.products.infraestructure.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapater implements IProductPersistencePort {

    private final ProductMapper mapper;
    private final SpringReactiveRepository repository;


    @Override
    public Mono<Product> save(Product product) {
        return repository.save(mapper.toEntity(product)).map(mapper::toDomain);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
               
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<Product> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

}
