package com.store.products.domain.exceptions;

public class ServiceTemporarilyUnavailableException extends RuntimeException {
    public ServiceTemporarilyUnavailableException(String message) {
        super(message);
    }
}
