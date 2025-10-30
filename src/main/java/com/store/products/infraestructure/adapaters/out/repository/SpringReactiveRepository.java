package com.store.products.infraestructure.adapaters.out.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.store.products.infraestructure.adapaters.out.entity.ProductEntity;

public interface SpringReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long> {

}
