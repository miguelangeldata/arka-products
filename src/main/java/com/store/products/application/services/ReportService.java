package com.store.products.application.services;

import com.store.products.domain.ports.in.ProductUsesCases;
import com.store.products.domain.ports.in.ReportUsesCases;
import com.store.products.domain.ports.out.persistence.ReportExportPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReportService implements ReportUsesCases {
    private final ProductUsesCases productUsesCases;
    private final ReportExportPort reportExportPort;
    @Override
    public Mono<String> generateLowStockReport(Integer threshold, String format) {
        return productUsesCases.getAllProducts()
                .filter(product ->product.getTotalStock()<=threshold)
                .collectList()
                .flatMap(lowStockProducts->{
                    if (lowStockProducts.isEmpty()) {
                        return Mono.just("No Found Products with low Stock (" + threshold + ") for export.");
                    }
                    return reportExportPort.exportReport(lowStockProducts,format);
                });
    }
}
