package com.store.products.domain.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReturnsAcceptedEvent {
    private String userId;
    private String email;
    private String message;
}
