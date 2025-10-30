package com.store.products.infraestructure.adapaters.in.controller;

import com.store.products.domain.ports.in.ReportUsesCases;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportUsesCases reportUsesCases;

    @GetMapping("/low-stock")
    public Mono<String> generateReport(
            @RequestParam(name = "threshold") Integer threshold,
            @RequestParam(name = "format", defaultValue = "CSV") String format
    ) {
        return reportUsesCases.generateLowStockReport(threshold, format);
    }

}
