package com.store.products.infraestructure.Exception;

import com.store.products.domain.exceptions.InsufficientStockException;
import com.store.products.domain.exceptions.ProductNotFoundException;
import com.store.products.domain.exceptions.ServiceTemporarilyUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalErrorWebExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInsufficientStock(InsufficientStockException ex, ServerWebExchange exchange) {
        return Mono.just(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().toString()
        ));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleProductNotFound(ProductNotFoundException ex, ServerWebExchange exchange) {
        return Mono.just(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().toString()
        ));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, ServerWebExchange exchange) {
        return Mono.just(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().toString()
        ));
    }
    @ExceptionHandler(ServiceTemporarilyUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<ErrorResponse> handleServiceUnavailable(ServiceTemporarilyUnavailableException ex, ServerWebExchange exchange) {
        return Mono.just(new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().toString()
        ));
    }
}