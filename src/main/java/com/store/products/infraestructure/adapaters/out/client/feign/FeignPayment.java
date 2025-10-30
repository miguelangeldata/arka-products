package com.store.products.infraestructure.adapaters.out.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@FeignClient(name = "payment-service",url = "http://localhost:8083/payments")
public interface FeignPayment {
    @PostMapping("/refunded/{orderId}")
    Mono<Void> refundedPayment(@PathVariable("orderId")String orderId);
}
