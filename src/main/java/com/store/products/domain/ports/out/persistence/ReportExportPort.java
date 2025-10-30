package com.store.products.domain.ports.out.persistence;

import com.store.products.domain.models.Product;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReportExportPort {
    Mono<String> exportReport(List<Product> products, String format);
}
