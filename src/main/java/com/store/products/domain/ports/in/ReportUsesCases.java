package com.store.products.domain.ports.in;

import reactor.core.publisher.Mono;

public interface ReportUsesCases {
    Mono<String> generateLowStockReport(Integer threshold, String format);
}
