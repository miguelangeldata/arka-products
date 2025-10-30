package com.store.products.domain.ports.out.client;

import reactor.core.publisher.Mono;

public interface PaymentPort {
    Mono<Void> RefundedPayment(String orderId);
}
