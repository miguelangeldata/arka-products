package com.store.products.infraestructure.Exception;

public record ErrorResponse(int status, String message, String path) {}
