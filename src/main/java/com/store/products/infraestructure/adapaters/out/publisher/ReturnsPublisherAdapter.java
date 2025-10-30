package com.store.products.infraestructure.adapaters.out.publisher;

import com.store.products.domain.events.ReturnsAcceptedEvent;
import com.store.products.domain.events.ReturnsRejectedEvent;
import com.store.products.domain.ports.out.publisher.ReturnsPublisherPort;

public class ReturnsPublisherAdapter implements ReturnsPublisherPort {
    @Override
    public void ReturnsAccepted(ReturnsAcceptedEvent event) {
        System.out.println("Returns was accepted payment refund will be sending");
    }

    @Override
    public void ReturnsRejectedEvent(ReturnsRejectedEvent event) {
        System.out.println("Returns was rejected ");
    }
}
