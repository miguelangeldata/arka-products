package com.store.products.domain.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReturnsRejectedEvent {
    private String userId;
    private String userEmail;
    private String message;
}
