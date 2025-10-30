package com.store.products.domain.ports.out.publisher;

import com.store.products.domain.events.ReturnsAcceptedEvent;
import com.store.products.domain.events.ReturnsRejectedEvent;

public interface ReturnsPublisherPort {
    void ReturnsAccepted(ReturnsAcceptedEvent event);
    void ReturnsRejectedEvent(ReturnsRejectedEvent event);
}
