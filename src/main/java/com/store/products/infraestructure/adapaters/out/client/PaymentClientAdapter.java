package com.store.products.infraestructure.adapaters.out.client;

import com.store.products.domain.ports.out.client.PaymentPort;
import com.store.products.infraestructure.adapaters.out.client.feign.FeignPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentClientAdapter implements PaymentPort {
    private final FeignPayment payment;

    @Override
    public Mono<Void> RefundedPayment( String orderId) {
        return payment.refundedPayment(orderId);
    }
}
