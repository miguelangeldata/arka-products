package com.store.products.application.services;

import com.store.products.domain.events.ReturnsAcceptedEvent;
import com.store.products.domain.events.ReturnsRejectedEvent;
import com.store.products.domain.models.Returns;
import com.store.products.domain.ports.in.ProductUsesCases;
import com.store.products.domain.ports.in.ReturnsUsesCases;
import com.store.products.domain.ports.out.persistence.ReturnsPersistencePort;
import com.store.products.domain.ports.out.client.PaymentPort;
import com.store.products.domain.ports.out.publisher.ReturnsPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReturnsService implements ReturnsUsesCases {
    private final ReturnsPersistencePort persistence;
    private final ReturnsPublisherPort publisher;
    private final ProductUsesCases productUsesCases;
    private final PaymentPort paymentPort;

    @Override
    public Mono<Returns> createReturn(Returns returns) {
        return persistence.save(returns);
    }

    @Override
    public void acceptReturn(UUID id) {
        getById(id).flatMap(returns -> {
            returns.switchToStockRecovery();
            Mono<Returns> saveOperation = persistence.save(returns);
            Flux<Void> recoveryOperations = Flux.fromIterable(returns.getItems())
                    .flatMap(item ->
                            productUsesCases.recoveryStockFromReturn(item.productId(), item.quantity())
                    );
            Mono<Void> refundOperation = paymentPort.RefundedPayment(returns.getOrderId());
            return saveOperation.thenMany(recoveryOperations)
                    .then()
                    .then(refundOperation)
                    .doOnSuccess(v -> {
                        String message = "The return was successfully accepted and yor coming was be refunded.";
                        ReturnsAcceptedEvent event = new ReturnsAcceptedEvent(
                                returns.getUserId(),
                                returns.getUserEmail(),
                                message
                        );
                        publisher.ReturnsAccepted(event);
                    });
        });

    }

    @Override
    public void rejectReturn(UUID id) {
        getById(id).flatMap(returns -> {
            returns.switchToRejected();
            Mono<Returns> saveOperation = persistence.save(returns);
            return saveOperation
                    .then()
                    .doOnSuccess(v -> {
                        String message = "The return was denied ";
                        ReturnsRejectedEvent event = new ReturnsRejectedEvent(
                                returns.getUserId(),
                                returns.getUserEmail(),
                                message
                        );
                        publisher.ReturnsRejectedEvent(event);
                    });
        });
    }

    @Override
    public Mono<Returns> getById(UUID id) {
        return persistence.getById(id);
    }

    @Override
    public Flux<Returns> getAllByUserId(String userId) {
        return persistence.getReturnsByUserId(userId);
    }

    @Override
    public Flux<Returns> getAllReturnsByProductId(Long productId) {
        return persistence.getReturnsByProductId(productId);
    }
}
